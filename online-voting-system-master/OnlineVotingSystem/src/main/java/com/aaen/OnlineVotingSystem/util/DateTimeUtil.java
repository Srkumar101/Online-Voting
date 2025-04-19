package com.aaen.OnlineVotingSystem.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author Mohammad Enayatullah
 *
 */
public class DateTimeUtil {

	public static Date convertToDate(LocalDateTime localDateTime) {

		if (Objects.nonNull(localDateTime))
			return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		return null;
	}
}
