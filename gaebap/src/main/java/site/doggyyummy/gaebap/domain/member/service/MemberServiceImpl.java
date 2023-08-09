package site.doggyyummy.gaebap.domain.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.exception.custom.*;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.global.security.util.SecurityUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AmazonS3 awsS3Client;
    @Override
    public void signUp(Member member) throws Exception{
        validateMemberRegistration(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    @Override
    public void modify(Member member, MultipartFile file) throws Exception{
        validateMemberModification(member);

        Member origin = SecurityUtil.getCurrentLoginMember();
        log.info("nickname: {}", member.getNickname());
        origin.setNickname(member.getNickname());
        if (member.getProfileUrl() != null) {//이전과 다를 거 없는 경우
            return;
        }
        uploadImageByFile(member, file);
    }

    @Override
    public Optional<Member> findByName(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public void validateNicknameModification(Member member) throws Exception{
        Member origin = SecurityUtil.getCurrentLoginMember();
        if (!isValidNicknameFormat(member.getNickname())) throw new InvalidNicknameFormatException();
        if (member.getNickname().equals(origin.getNickname())) return;
        if (isDuplicateNickname(member.getNickname())) throw new DuplicateNicknameException();
    }


    @Override
    public void validateRegistrationUsername(String registerName) throws InvalidNameFormatException, DuplicateUsernameException{
        if (isDuplicateName(registerName)) throw new DuplicateEmailException();
    }

    @Override
    public void validateRegistrationNickname(String nickname) throws InvalidNameFormatException, DuplicateNicknameException {
        if (!isValidNicknameFormat(nickname)) throw new InvalidNicknameFormatException();
        if (isDuplicateNickname(nickname)) throw new DuplicateNicknameException();
    }

    //=============================================================================================
    private boolean isDuplicateName(String name){
        return memberRepository.existsByUsername(name);
    }

    private boolean isDuplicateNickname(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    private boolean isValidNicknameFormat(String nickname){
        Integer length = nickname.length();
        log.info("nickname : {}", nickname);
        if (length == 0 || length > 30) return false;
        return true;
    }

    private void validateMemberRegistration(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        validateRegistrationUsername(member.getUsername());
        validateRegistrationNickname(member.getNickname());
    }

    private void validateMemberModification(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        validateNicknameModification(member);
    }

    private void uploadImageByFile(Member member, MultipartFile file) throws Exception{
        Member origin = SecurityUtil.getCurrentLoginMember();
        String imgKey = null;
        UUID uuid = UUID.nameUUIDFromBytes(origin.getUsername().getBytes());
        String bucketName="sh-bucket";

        imgKey = "profile/" + uuid;

       if (file == null || file.isEmpty()) {//새로 등록하지 않는 경우는 삭제함
            if (imgKey != null && awsS3Client.doesObjectExist(bucketName,imgKey)) awsS3Client.deleteObject(bucketName, imgKey);
            member.setProfileUrl(null);
            return;
       }

       ObjectMetadata objMeta = new ObjectMetadata();
       objMeta.setContentType(file.getContentType());
       objMeta.setContentLength(file.getInputStream().available());

       awsS3Client.putObject("sh-bucket", imgKey, file.getInputStream(), objMeta);
       String s3Url = awsS3Client.getUrl("sh-bucket", imgKey).toString();

       origin.setProfileUrl(s3Url);

    }

    public void uploadImageByUrl(Member member) throws Exception{//소셜 로그인으로 처음 가입한 경우
        if (member.getProfileUrl() == null) return;
        URL url = new URL(member.getProfileUrl());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.connect();
        String contentType = connection.getContentType();
        connection.disconnect();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);

        InputStream inputStream = url.openStream();

        String bucketName="sh-bucket";
        UUID uuid = UUID.nameUUIDFromBytes(member.getUsername().getBytes());
        String imgKey = "profile/" + uuid;
        awsS3Client.putObject(new PutObjectRequest(bucketName, imgKey, inputStream, objectMetadata));
        member.setProfileUrl(awsS3Client.getUrl(bucketName, imgKey).toString());
        inputStream.close();
    }


    //TODO : 비밀번호 검증하는 부분이 있어야 함
}
