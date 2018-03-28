package com.databilize.fastcascade.annotations;

/**
 * 用于提供class的元信息
 * 
 * @author 0x3D
 *
 */
public @interface ClassPreamble {
	String author();

	String date();

	int currentRevision() default 1;

	String lastModified() default "N/A";

	String lastModifiedBy() default "N/A";

	String[] reviewers() default {};
}
