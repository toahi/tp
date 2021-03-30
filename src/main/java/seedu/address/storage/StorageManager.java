package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of EventBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EventBookStorage eventBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code EventBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(EventBookStorage eventBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.eventBookStorage = eventBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ EventBook methods ==============================

    @Override
    public Path getEventBookFilePath() {
        return eventBookStorage.getEventBookFilePath();
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException {
        return readEventBook(eventBookStorage.getEventBookFilePath());
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventBookStorage.readEventBook(filePath);
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, eventBookStorage.getEventBookFilePath());
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventBookStorage.saveEventBook(eventBook, filePath);
    }

}
