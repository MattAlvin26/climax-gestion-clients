package climax.app.config;


import climax.app.database.entity.ClientEntity;
import climax.app.model.ClientDto;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOption;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DozerConfig {
    private final BeanMappingBuilder builder=new BeanMappingBuilder(){
        protected void configure(){
    //mapping(ClientDto.class, ClientEntity.class, TypeMappingOptions.mapNull(false));
        }
    };

    @Bean
    public Mapper buildDozerMapper(){
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(builder)
                .build();
    }
}

