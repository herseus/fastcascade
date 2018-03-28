package com.databilize.fastcascade.annotations;

import java.lang.annotation.Documented;

/**
 * 用于提供class的元信息
 * 
 * @author 0x3D
 *
 */
@Documented
public @interface ClassPreamble {
	String author();

	String date();

	int currentRevision() default 1;

	String lastModified() default "N/A";

	String lastModifiedBy() default "N/A";

	String[] reviewers() default {};
}
