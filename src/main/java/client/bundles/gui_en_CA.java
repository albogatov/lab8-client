package client.bundles;

import java.util.ListResourceBundle;

public class gui_en_CA extends ListResourceBundle {
    private static final Object[][] contents = {
            {"AddError", "Error adding an element"},
            {"AddSuccess", "The element is successfully added"},
            {"CommandError", "Error when accessing the server"},
            {"AddIfMinError", "Error adding a minimum element"},
            {"AddIfMinSuccess", "The minimum element has been successfully added."},
            {"CountByStatusMessage", "Elements with the same status:"},
            {"ScriptSuccess", "The script was successful"},
            {"ScriptError", "An error occurred during the script"},
            {"ExitMessage", "To new meetings"},
            {"HelpMessage", "Reference:\n" +
                    "At the bottom of the interface there are buttons for interacting with the base. To perform actions that change the contents of the database, you must first select a row of the table. You can also double-click on the required row of the table to change the object.\n" +
                    "The \"Clear\" command removes from the database only those objects that belong to you.\n" +
                    "You can go to the \"Visualization\" section to see a visual representation of the database objects. Double clicking on the object will enable you to edit it."},
            {"InfoMessage", "Collection information:\n" +
                    " Access date: {0}\n" +
                    " Type: {1}\n" +
                    " Size: {2}"},
            {"LoginSuccess", "The entrance is successful!"},
            {"LoginError", "Invalid entry data!"},
            {"PrintOrgsMessage", "Unique organizations:"},
            {"RegisterSuccess", "Registration is successful!"},
            {"RegisterError", "Error when registering, try later."},
            {"RemoveSuccess", "The element is deleted successfully!"},
            {"RemoveError", "Error deleting an item."},
            {"RemoveGreaterSuccess", "Big elements are removed."},
            {"RemoveGreaterError", "Error deleting large elements."},
            {"RemoveLowerSuccess", "Smaller elements are removed."},
            {"RemoveLowerError", "Error when removing smaller elements."},
            {"SaveMessage", "The collection is saved to the file."},
            {"Empty", "Khm."},
            {"UpdateSuccess", "Changing the object is successful!"},
            {"UpdateError", "Error when changing the object."},
            {"IdColumn", "Identifier"},
            {"NameColumn", "Name"},
            {"XColumn", "X."},
            {"YColumn", "Y."},
            {"SalaryColumn", "Salary"},
            {"EndDateColumn", "Date of termination of the contract"},
            {"CreationDateColumn", "Date of adding to the database"},
            {"PositionColumn", "Position"},
            {"StatusColumn", "Status"},
            {"OrgColumn", "Organization"},
            {"OrgTypeColumn", "Type of organization"},
            {"AnnualTurnoverColumn", "Annual revenue"},
            {"StreetColumn", "Street"},
            {"PostalCodeColumn", "Index"},
            {"UserColumn", "User"},
            {"RefreshButton", "Refresh"},
            {"AddButton", "Add"},
            {"UpdateButton", "Change"},
            {"RemoveButton", "Delete"},
            {"RemoveGreaterButton", "Remove large"},
            {"RemoveLowerButton", "Remove smaller"},
            {"ClearButton", "Clear"},
            {"HelpButton", "Help"},
            {"InfoButton", "Information"},
            {"CountByStatusButton", "Count by status"},
            {"PrintUniqueOrgsButton", "Unique organizations"},
            {"SaveButton", "Save to file"},
            {"AddIfMinButton", "Add if less"},
            {"ExecuteScriptButton", "Run script"},
            {"VisualMapTab", "Visualization"},
            {"DataTableTab", "Table"},
            {"PortUnavailableError", "The server is currently unavailable."},
            {"ConnectionError", "Connection to the server failed."},
            {"AddressError", "The connection address is not found."},
            {"FatalConnectionError", "Natasha, are you sleeping? For 6 o'clock in the morning, Natasha. Get up, we dropped everything there. We dropped everything at all, Natasha, honestly."},
            {"LoginLabel", "Authorization"},
            {"LoginFieldText", "Enter login"},
            {"PwdFieldText", "Enter password"},
            {"LoginButton", "Sign in"},
            {"RegisterButton", "Register"},
            {"TryLater", "Try to execute this action later."},
            {"DoneButton", "Ready"},
            {"NameLabel", "Name"},
            {"XLabel", "X."},
            {"YLabel", "Y."},
            {"SalaryLabel", "Salary"},
            {"EndDateLabel", "Date of termination of the contract"},
            {"PositionLabel", "Position"},
            {"StatusLabel", "Status"},
            {"OrgLabel", "Organization"},
            {"OrgTypeLabel", "Type of organization"},
            {"AnnTurnoverLabel", "Annual revenue"},
            {"StreetLabel", "Street"},
            {"PostalCodeLabel", "Index"},
            {"NamePrompt", "Enter your name"},
            {"XPrompt", "X."},
            {"YPrompt", "Y."},
            {"SalaryPrompt", "Enter the salary"},
            {"EndDatePrompt", "Specify the Date Termination of the Contract"},
            {"PositionPrompt", "Choose a position"},
            {"StatusPrompt", "Select Status"},
            {"OrgPrompt", "Enter the name of the organization"},
            {"OrgTypePrompt", "Select the type of organization"},
            {"AnnTurnoverPrompt", "Enter the annual revenue"},
            {"StreetPrompt", "Enter the street"},
            {"PostalCodePrompt", "Enter the index"},
            {"ObjectNotChosen", "First select an object!"},
            {"WrongEnteredData", "Entered incorrect data!"},
            {"WorkerDisplay", "Name: {0}\n" +
                    "Salary: {1}\n" +
                    "Contract end date: {2}\n" +
                    "Position: {3}\n" +
                    "Status: {4}\n" +
                    "Organization: {5}\n" +
                    "A type: {6}"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
