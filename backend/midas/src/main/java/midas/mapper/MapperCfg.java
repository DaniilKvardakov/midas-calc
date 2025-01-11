package midas.mapper;

import midas.annotations.NoCommentsNeeded;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@NoCommentsNeeded
@Component
public class MapperCfg {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
