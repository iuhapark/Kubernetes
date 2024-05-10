package com.example.kubernetes.common.component.files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Component
@Getter
@Builder
public class imageVo {
    private Long ImageId;
    String name;
    Long lastModified;
    Long lastModifiedDate;
    String type;
    String webkitRelativePath;
    Long size;

}
