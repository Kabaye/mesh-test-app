package com.test.model;

import com.test.entity.EmailData;
import com.test.entity.PhoneData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UpdateUserRequest {
    private List<PhoneData> newPhoneData;
    private List<EmailData> newEmailData;
}
