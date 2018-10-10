package com.kotlarz.fly4freenotifier.service.backup;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Service
public class DatabaseBackupService
{
    private static final Integer MAX_BACKUP_DAYS = 7;

    private static final String BACKUPS_FOLDER_NAME = "backups";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat( "yyyy-MM-dd_HH_mm_ss" );

    @Scheduled( fixedRate = 1000 * 3600 * 12 )
    public void runBackup()
    {
        createBackup();
        cleanupBackups();
    }

    private void createBackup()
    {
        log.info( "Creating database backup" );
        Date now = new Date();
        String fileName = "backup_" + dateTimeFormatter.format( now ) + ".zip";
        jdbcTemplate.execute( "BACKUP TO '" + BACKUPS_FOLDER_NAME + "/" + fileName + "'" );
    }

    private void cleanupBackups()
    {
        log.info( "Removing old backups" );
        DateTime now = DateTime.now();
        DateTime maxMinusDate = now.minusDays( MAX_BACKUP_DAYS );

        Arrays.asList( new File( BACKUPS_FOLDER_NAME ).listFiles() ).forEach( file -> {
            try
            {
                DateTime backupDate = getBackupDate( file.getName() );
                if ( backupDate.isBefore( maxMinusDate ) )
                {
                    log.info( "Removing file '{}'", file.getName() );
                    file.delete();
                }
            }
            catch ( IllegalBackupFileNameException ex )
            {
                log.error( "File '" + file.getName() + "' has incorrect file name format. Deleting", ex );
                file.delete();
            }
        } );
    }

    private DateTime getBackupDate( String fileName )
                    throws IllegalBackupFileNameException
    {
        try
        {
            String withoutExtension = FilenameUtils.removeExtension( fileName );
            return new DateTime( dateTimeFormatter.parse( withoutExtension.replace( "backup_", "" ) ) );
        }
        catch ( Exception ex )
        {
            throw new IllegalBackupFileNameException( ex );
        }
    }
}
