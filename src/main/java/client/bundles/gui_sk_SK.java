package client.bundles;

import java.util.ListResourceBundle;

public class gui_sk_SK extends ListResourceBundle  {
    private static final Object[][] contents = {
            {"AddError", "Chyba pridaním prvku"},
            {"AddSuccess", "Prvok je úspešne pridaný"},
            {"CommandError", "Pri prístupe k serveru"},
            {"AddIfMinError", "Chyba pridaním minimálneho prvku"},
            {"AddIfMinSuccess", "Minimálny prvok bol úspešne pridaný."},
            {"CountByStatusMessage", "Prvky s rovnakým stavom:"},
            {"ScriptSuccess", "Skript bol úspešný"},
            {"ScriptError", "Počas skriptu sa vyskytla chyba"},
            {"ExitMessage", "Na nové stretnutia"},
            {"HelpMessage", "Referencia:\n" +
                    "V spodnej časti rozhrania sa nachádzajú tlačidlá pre interakciu so základňou. Ak chcete vykonať akcie, ktoré menia obsah databázy, musíte najskôr vybrať riadok tabuľky. Objekt môžete zmeniť aj dvojitým kliknutím na požadovaný riadok tabuľky.\n" +
                    "Príkaz \"Vymazať\" odstráni z databázy iba tie objekty, ktoré vám patria.\n" +
                    "Môžete prejsť do časti „Vizualizácia“ a pozrieť si vizuálne znázornenie databázových objektov. Dvojité kliknutie na objekt vám umožní upraviť ho.\n"},
            {"InfoMessage", "Informácie o zbierke:\n" +
                    " Dátum prístupu: {0}\n" +
                    " Typ: {1}\n" +
                    " Veľkosť: {2}"},
            {"LoginSuccess", "Vstup je úspešný!"},
            {"LoginError", "Neplatné vstupné údaje!"},
            {"PrintOrgsMessage", "Unikátne organizácie:"},
            {"RegisterSuccess", "Registrácia je úspešná!"},
            {"RegisterError", "Vyskúšajte chybu pri registrácii, skúste neskôr."},
            {"RemoveSuccess", "Prvok sa úspešne vymaže!"},
            {"RemoveError", "Chyba vymazania položky."},
            {"RemoveGreaterSuccess", "Veľké prvky sú odstránené."},
            {"RemoveGreaterError", "Chyba vymazanie veľkých prvkov."},
            {"RemoveLowerSuccess", "Menšie prvky sa odstránia."},
            {"RemoveLowerError", "Pri odstraňovaní menších prvkov."},
            {"SaveMessage", "Kolekcia sa uloží do súboru."},
            {"Empty", "Khm."},
            {"UpdateSuccess", "Zmena objektu je úspešná!"},
            {"UpdateError", "Pri zmene objektu."},
            {"IdColumn", "Identifikátor"},
            {"NameColumn", "Názov"},
            {"XColumn", "X."},
            {"YColumn", "Y."},
            {"SalaryColumn", "Plat"},
            {"EndDateColumn", "Dátum ukončenia zmluvy"},
            {"CreationDateColumn", "Dátum pridania do databázy"},
            {"PositionColumn", "Pozícia"},
            {"StatusColumn", "Postavenie"},
            {"OrgColumn", "Organizácia"},
            {"OrgTypeColumn", "Typ organizácie"},
            {"AnnualTurnoverColumn", "Ročné príjmy"},
            {"StreetColumn", "Ulica"},
            {"PostalCodeColumn", "Index"},
            {"UserColumn", "Užívateľ"},
            {"RefreshButton", "Obnoviť"},
            {"AddButton", "Pridať"},
            {"UpdateButton", "Zmena"},
            {"RemoveButton", "Vymazať"},
            {"RemoveGreaterButton", "Odstrániť veľké"},
            {"RemoveLowerButton", "Odstrániť menšie"},
            {"ClearButton", "Jasný"},
            {"HelpButton", "Pomoc"},
            {"InfoButton", "Informácie"},
            {"CountByStatusButton", "Vypočítajte podľa stavu"},
            {"PrintUniqueOrgsButton", "Unikátne organizácie:"},
            {"SaveButton", "Uložiť do súboru"},
            {"AddIfMinButton", "Pridať, ak je menej"},
            {"ExecuteScriptButton", "Spustiť skript"},
            {"VisualMapTab", "Vizualizácia"},
            {"DataTableTab", "Stôl"},
            {"PortUnavailableError", "Server je momentálne nedostupný."},
            {"ConnectionError", "Pripojenie k serveru zlyhalo."},
            {"AddressError", "Adresa pripojenia sa nenašla."},
            {"FatalConnectionError", "Natasha, spíte? Za 6 hodín ráno, Natasha. Vstaň, všetko sme tam spadli. Všetko sme spadli vôbec, Natasha, čestne."},
            {"LoginLabel", "Povolenie"},
            {"LoginFieldText", "Zadajte prihlásenie"},
            {"PwdFieldText", "Zadajte heslo"},
            {"LoginButton", "Prihlásiť sa"},
            {"RegisterButton", "Registrovať"},
            {"TryLater", "Pokúste sa vykonať túto akciu neskôr."},
            {"DoneButton", "Pripravený"},
            {"NameLabel", "Názov"},
            {"XLabel", "X."},
            {"YLabel", "Y."},
            {"SalaryLabel", "Plat"},
            {"EndDateLabel", "Dátum ukončenia zmluvy"},
            {"PositionLabel", "Pozícia"},
            {"StatusLabel", "Postavenie"},
            {"OrgLabel", "Organizácia"},
            {"OrgTypeLabel", "Typ organizácie"},
            {"AnnTurnoverLabel", "Ročné príjmy"},
            {"StreetLabel", "Ulica"},
            {"PostalCodeLabel", "Index"},
            {"NamePrompt", "Zadajte svoje meno"},
            {"XPrompt", "X."},
            {"YPrompt", "Y."},
            {"SalaryPrompt", "Zadajte plat"},
            {"EndDatePrompt", "Uveďte dátum ukončenia zmluvy"},
            {"PositionPrompt", "Vyberte si pozíciu"},
            {"StatusPrompt", "Vyberte stav"},
            {"OrgPrompt", "Zadajte názov organizácie"},
            {"OrgTypePrompt", "Vyberte typ organizácie"},
            {"AnnTurnoverPrompt", "Zadajte ročné príjmy"},
            {"StreetPrompt", "Zadajte ulicu"},
            {"PostalCodePrompt", "Zadajte index"},
            {"ObjectNotChosen", "Najprv vyberte objekt!"},
            {"WrongEnteredData", "Zadané nesprávne údaje!"},
            {"WorkerDisplay", "Názov: {0}\n" +
                    "Plat: {1}\n" +
                    "Dátum ukončenia zmluvy: {2}\n" +
                    "Pozícia: {3}\n" +
                    "Postavenie: {4}\n" +
                    "Organizácia: {5}\n" +
                    "Typ: {6}"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
