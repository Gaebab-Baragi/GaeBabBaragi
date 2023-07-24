package site.doggyyummy.gaebap.domain.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.domain.recipe.dto.RecipeUploadReqDto;
import site.doggyyummy.gaebap.domain.recipe.dto.RecipeUploadResDto;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.repository.RecipeRepository;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public RecipeUploadResDto uploadRecipe(RecipeUploadReqDto reqDto){
        Member member=new Member();
        member.setName(reqDto.getMember().getName());
        Member savedMember = memberRepository.save(member);

        Recipe recipe=new Recipe();
        recipe.setMember(member);
        recipe.setTitle(reqDto.getTitle());
        recipe.setDescription(reqDto.getDescription());
        Recipe savedRecipe = recipeRepository.save(recipe);

        return new RecipeUploadResDto(savedRecipe.getTitle(),savedMember);
    }
}
