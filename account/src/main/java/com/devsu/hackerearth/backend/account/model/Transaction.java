package com.devsu.hackerearth.backend.account.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends Base {

	private Date date;
	private String type;
	private double amount;
	private double balance;

	// @Column(name = "account_id")
	// private Long accountId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;
}
