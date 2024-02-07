package com.example.demo.global.util;

public class RegExpPattern {

	/**
	 * 회원 정규식
	 */
	public static final String ID_PATTERN = "^[a-z0-9]{4,16}$";
	public static final String PWD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";

	/**
	 * Masking 정규식
	 */
	public static final String ID_MASKING_PATTERN = "(?<=.{2}).";
	public static final String EMAIL_MASKING_PATTERN = "(?<=.{2}).(?=.*@)";
}