package com.bpi.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.util.Locale;
import java.util.Optional;

/**
 * Date util
 * 
 * @author Joe
 * 
 */
@Slf4j
public class DateUtil {

	private static final Chronology CHRONOLOGY = MinguoChronology.INSTANCE;

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	public static final DateTimeFormatter DATE_FORMAT_YYYYMMDD_HHMMSS = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
	public static final DateTimeFormatter DATE_FORMAT_YYYYMMDD_T_HHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	public static final DateTimeFormatter DATE_MINGO_YYYMMDD_FORMATTER = DateTimeFormatter.ofPattern("yyyMMdd");

	public static final DateTimeFormatter DATE_MINGO_FORMATTER = new DateTimeFormatterBuilder().parseLenient()
			.appendPattern("yyyMMdd").toFormatter().withChronology(CHRONOLOGY).withDecimalStyle(DecimalStyle.of(Locale.getDefault()));
	/**
	 * 0yyyMMdd => LocalDate
	 *
	 * @param minGoString
	 * @return
	 */
	public LocalDate formatMinGoToLocalDate(String minGoString) {
		try {
			ChronoLocalDate date = CHRONOLOGY.date(DATE_MINGO_FORMATTER.parse(minGoString));
			return LocalDate.from(date);
		} catch (Exception e) {
			log.error("民國轉localDate失敗:{}", minGoString);
			throw e;
		}

	}


	/**
	 * yyyMMdd => LocalDate
	 *
	 * @param minGoString
	 * @return
	 */
	public static LocalDate formatYYYMMDDToLocalDate(String minGoString) {
		try {
			ChronoLocalDate date = CHRONOLOGY.date(DATE_MINGO_YYYMMDD_FORMATTER.parse(minGoString));
			return LocalDate.from(date);
		} catch (Exception e) {
			log.error("民國轉localDate失敗:{}", minGoString);
			throw e;
		}

	}

	public static String getNowDate() {
		return LocalDate.now().format(DATE_FORMATTER);
	}

	public static String getNowDateTime() {
		return LocalDateTime.now().format(DATE_FORMAT_YYYYMMDD_HHMMSS);
	}

	public static String dateTimeToString(LocalDateTime dateTime) {
		return dateTime.format(DATE_FORMAT_YYYYMMDD_HHMMSS);
	}

	public static String dateToFormat(String format, LocalDate date) {
		try {
			var dtf = DateTimeFormatter.ofPattern(format);
			return date.format(dtf);
		} catch (Exception e) {
			log.error("LocalDate To String failed : {}", e);
			throw e;
		}
	}

	public static String dateTimeToFormat(String format, LocalDateTime dateTime) {
		try {
			var dtf = DateTimeFormatter.ofPattern(format);
			return dateTime.format(dtf);
		} catch (Exception e) {
			log.error("LocalDateTime To String failed : {}", e);
			throw e;
		}
	}

	/**
	 * 將 ex: yyyy-MM-dd'T'HH:mm:ss 時間格式字串 轉 yyyy/MM/dd hh:mm:ss 時間格式字串
	 * 
	 * @param updated
	 * @return
	 */
	public static String updatedFormat(String updated) {
		LocalDateTime localDateTime = LocalDateTime.parse(updated, DATE_FORMAT_YYYYMMDD_T_HHMMSS);
		return localDateTime.format(DATE_FORMAT_YYYYMMDD_HHMMSS);
	}

	public static String formatLocalDateToMingoString(String ceDateStr, DateTimeFormatter localDateTimeFormatter, DateTimeFormatter mingoFormatter) {
		try {
			if (Optional.ofNullable(ceDateStr).orElse("").isBlank()) {
				log.debug("ceDateStr is null or blank");
				return "0";
			}

			if ("0".equals(ceDateStr)) {
				log.debug("ceDateStr is 0");
				return "0";
			}

			if ("99991231".equals(ceDateStr)) {
				return "99991231";
			}

			LocalDate trans = LocalDate.parse(ceDateStr, localDateTimeFormatter);
			return MinguoDate.from(trans).format(mingoFormatter);
		} catch (Exception e) {
			log.error("formatLocalDateToMingoString date:{} formatter:{} error:{}", ceDateStr, localDateTimeFormatter, e);
			throw e;
		}
	}


	
}
