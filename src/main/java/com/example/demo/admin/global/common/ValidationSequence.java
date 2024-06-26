package com.example.demo.admin.global.common;

import jakarta.validation.GroupSequence;

@GroupSequence({ValidationGroups.NotBlankGroup.class, ValidationGroups.PatternGroup.class, ValidationGroups.SizeGroup.class})
public interface ValidationSequence {
}