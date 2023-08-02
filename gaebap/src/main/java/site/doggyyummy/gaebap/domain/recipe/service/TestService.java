package site.doggyyummy.gaebap.domain.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.recipe.dto.TestRequestDto;
import site.doggyyummy.gaebap.domain.recipe.entity.Address;
import site.doggyyummy.gaebap.domain.recipe.entity.Person;
import site.doggyyummy.gaebap.domain.recipe.repository.PersonRepository;
import site.doggyyummy.gaebap.domain.recipe.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestService {
    private final PersonRepository personRepository;
    private final TestRepository testRepository;
    @Transactional
    public void save(TestRequestDto reqDto){
        Person person=Person.builder().name(reqDto.getName()).age(reqDto.getAge()).build();
        List<Address> addresses=new ArrayList<>();
        for(TestRequestDto.AddressDto a:reqDto.getAddresses()){
            Address address=Address.builder().person(person).si(a.getSi()).gu(a.getGu()).build();
            addresses.add(address);
        }
        personRepository.save(person);
        person.addAddress(addresses);
        testRepository.saveAll(addresses);
    }
}
