package com.fxlabs.fxt.codegen.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AutoCodeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Database> databases;

}


