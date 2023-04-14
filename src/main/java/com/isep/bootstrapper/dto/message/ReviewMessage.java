package com.isep.bootstrapper.dto.message;

import com.isep.bootstrapper.enumarate.ApprovalStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMessage {

    private Long idReview;
    private ApprovalStatus approvalStatus;

}
