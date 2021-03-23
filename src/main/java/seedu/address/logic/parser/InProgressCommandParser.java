package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.stream.Stream;

import seedu.address.logic.commands.InProgressCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPriority;
import seedu.address.model.event.EventStatus;

public class InProgressCommandParser implements Parser<InProgressCommand> {
    private static final EventStatus IN_PROGRESS_EVENT_STATUS = EventStatus.IN_PROGRESS;

    /**
     * Parses the given {@code String} of arguments in the context of the InProgressCommand
     * and returns an InProgressCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InProgressCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InProgressCommand.MESSAGE_USAGE));
        }

        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Event event;

        if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
            EventPriority priority = ParserUtil.parseEventPriority(argMultimap.getValue(PREFIX_PRIORITY).get());
            event = new Event(eventName, IN_PROGRESS_EVENT_STATUS, priority, description);
        } else {
            event = new Event(eventName, IN_PROGRESS_EVENT_STATUS, EventPriority.NONE, description);
        }

        return new InProgressCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
