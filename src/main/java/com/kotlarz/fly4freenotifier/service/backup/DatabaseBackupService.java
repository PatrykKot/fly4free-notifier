package com.kotlarz.fly4freenotifier.service.backup;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Service
public class DatabaseBackupService {
    private static final Integer MAX_BACKUP_DAYS = 7;

    private static final String BACKUPS_FOLDER_NAME = "backups";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 1000 * 24 * 3600 * 12, initialDelay = 1000 * 24 * 3600 * 12)
    public void runBackup() {
        createBackup();
        cleanupBackups();
    }

    private void createBackup() {
        log.info("Creating database backup");
        Date now = new Date();
        String fileName = "backup_" + now.getTime() + ".zip";
        jdbcTemplate.execute("BACKUP TO '" + BACKUPS_FOLDER_NAME + "/" + fileName + "'");
    }

    private void cleanupBackups() {
        log.info("Removing old backups");
        DateTime now = DateTime.now();
        DateTime maxMinusDate = now.minusDays(MAX_BACKUP_DAYS);

        Arrays.asList(new File(BACKUPS_FOLDER_NAME).listFiles()).forEach(file -> {
            String fileName = FilenameUtils.removeExtension(file.getName());
            DateTime backupDate = getBackupDate(fileName);

            if (backupDate.isBefore(maxMinusDate)) {
                log.info("Removing file '{}'", fileName);
                file.delete();
            }
        });
    }

    private DateTime getBackupDate(String fileName) {
        return new DateTime(Long.parseLong(fileName.split("_")[1]));
    }
}
