package com.makiia.crosscutting.translate;
import com.makiia.crosscutting.domain.model.EntyRecmaetarivalorDto;
import com.makiia.crosscutting.exceptions.Main.EBusinessException;
import com.makiia.crosscutting.patterns.Translator;
import com.makiia.crosscutting.persistence.entity.EntyRecmaetarivalor;
import com.makiia.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;
@Component
public class EntyRecmaetarivalorDtoToEntityTranslate implements Translator<EntyRecmaetarivalorDto, EntyRecmaetarivalor> {

    @Override
    public EntyRecmaetarivalor translate(EntyRecmaetarivalorDto input) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(GsonUtil.getGson().toJson(input), EntyRecmaetarivalor.class);
    }
}
