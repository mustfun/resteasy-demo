package com.dzy.resteasy.facade.resteasy;

import com.dzy.resteasy.result.Result;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/22
 * @since 1.0
 */
public interface ExternalFacade {

    Result<Boolean> getCity(Long id);

    public Result<Boolean> getObscureCity(Long id);
}
