package client.bundles;

import java.util.ListResourceBundle;

public class gui_sq_AL extends ListResourceBundle {
    private static final Object[][] contents = {
            {"AddError", "Gabim duke shtuar një element"},
            {"AddSuccess", "Elementi është shtuar me sukses"},
            {"CommandError", "Gabim gjatë qasjes në server"},
            {"AddIfMinError", "Gabim duke shtuar një element minimal"},
            {"AddIfMinSuccess", "Elementi minimal është shtuar me sukses."},
            {"CountByStatusMessage", "Elementet me të njëjtin status:"},
            {"ScriptSuccess", "Script ishte i suksesshëm"},
            {"ScriptError", "Një gabim ndodhi gjatë skriptit"},
            {"ExitMessage", "Në takime të reja"},
            {"HelpMessage", "Referenca:\n" +
                    "Në pjesën e poshtme të ndërfaqes ka butona për bashkëveprim me bazën. Për të kryer veprime që ndryshojnë përmbajtjen e bazës së të dhënave, së pari duhet të zgjidhni një rresht të tabelës. Ju gjithashtu mund të klikoni dy herë në rreshtin e kërkuar të tabelës për të ndryshuar objektin.\n" +
                    "Komanda \"Pastro\" heq nga baza e të dhënave vetëm ato objekte që ju përkasin.\n" +
                    "Ju mund të shkoni në seksionin \"Vizualizimi\" për të parë një paraqitje vizuale të objekteve të bazës së të dhënave. Klikimi i dyfishtë mbi objektin do t'ju lejojë ta redaktoni."},
            {"InfoMessage", "Informacioni i koleksionit:\n" +
                    " Data e hyrjes: {0}\n" +
                    " Lloji: {1}\n" +
                    " Madhësia: {2}"},
            {"LoginSuccess", "Hyrja është e suksesshme!"},
            {"LoginError", "Të dhënat e hyrjes së pavlefshme!"},
            {"PrintOrgsMessage", "Organizatat unike:"},
            {"RegisterSuccess", "Regjistrimi është i suksesshëm!"},
            {"RegisterError", "Provoni një gabim kur regjistroheni, provoni më vonë."},
            {"RemoveSuccess", "Elementi fshihet me sukses!"},
            {"RemoveError", "Gabim në fshirjen e një artikulli."},
            {"RemoveGreaterSuccess", "Elementet e mëdha hiqen."},
            {"RemoveGreaterError", "Gabim në fshirjen e elementeve të mëdhenj."},
            {"RemoveLowerSuccess", "Elementet më të vogla janë hequr."},
            {"RemoveLowerError", "Gabim gjatë heqjes së elementeve më të vogla."},
            {"SaveMessage", "Mbledhja ruhet në skedar."},
            {"Empty", "Khm."},
            {"UpdateSuccess", "Ndryshimi i objektit është i suksesshëm!"},
            {"UpdateError", "Gabim kur ndryshon objektin."},
            {"IdColumn", "Identifikues"},
            {"NameColumn", "Emër"},
            {"XColumn", "X."},
            {"YColumn", "Y."},
            {"SalaryColumn", "Rrogë"},
            {"EndDateColumn", "Data e ndërprerjes së kontratës"},
            {"CreationDateColumn", "Data e shtimit të bazës së të dhënave"},
            {"PositionColumn", "Pozicion"},
            {"StatusColumn", "Status"},
            {"OrgColumn", "Organizatë"},
            {"OrgTypeColumn", "Lloji i organizatës"},
            {"AnnualTurnoverColumn", "Ardhurat vjetore"},
            {"StreetColumn", "Rrugë"},
            {"PostalCodeColumn", "Indeks"},
            {"UserColumn", "Përdorues"},
            {"RefreshButton", "Rifreskoj"},
            {"AddButton", "Shtoj"},
            {"UpdateButton", "Ndryshoj"},
            {"RemoveButton", "Fshij"},
            {"RemoveGreaterButton", "Hiqni të mëdha"},
            {"RemoveLowerButton", "Hiqni më të vogla"},
            {"ClearButton", "Qartë"},
            {"HelpButton", "Ndihmoj"},
            {"InfoButton", "Informacion"},
            {"CountByStatusButton", "Llogarisni sipas statusit"},
            {"PrintUniqueOrgsButton", "Organizata unike"},
            {"SaveButton", "Ruaj në skedar"},
            {"AddIfMinButton", "Shtoni nëse më pak"},
            {"ExecuteScriptButton", "Skript"},
            {"VisualMapTab", "Vizualizim"},
            {"DataTableTab", "Tryezë"},
            {"PortUnavailableError", "Serveri aktualisht nuk është i disponueshëm."},
            {"ConnectionError", "Lidhja me serverin dështoi."},
            {"AddressError", "Adresa e lidhjes nuk gjendet."},
            {"FatalConnectionError", "Natasha, po fle? Për orën 6 të mëngjesit, Natasha. Ngrihuni, kemi rënë gjithçka atje. Ne hoqëm gjithçka në të gjitha, Natasha, me ndershmëri."},
            {"LoginLabel", "Autorizim"},
            {"LoginFieldText", "Futni hyrjen"},
            {"PwdFieldText", "Shkruani fjalëkalimin"},
            {"LoginButton", "Hyni"},
            {"RegisterButton", "Regjistrohem"},
            {"TryLater", "Mundohuni të ekzekutoni këtë veprim më vonë."},
            {"DoneButton", "I gatshëm"},
            {"NameLabel", "Emër"},
            {"XLabel", "X."},
            {"YLabel", "Y."},
            {"SalaryLabel", "Rrogë"},
            {"EndDateLabel", "Data e ndërprerjes së kontratës"},
            {"PositionLabel", "Pozicion"},
            {"StatusLabel", "Status"},
            {"OrgLabel", "Organizatë"},
            {"OrgTypeLabel", "Lloji i organizatës"},
            {"AnnTurnoverLabel", "Ardhurat vjetore"},
            {"StreetLabel", "Rrugë"},
            {"PostalCodeLabel", "Indeks"},
            {"NamePrompt", "Shkruaj emrin tend"},
            {"XPrompt", "X."},
            {"YPrompt", "Y."},
            {"SalaryPrompt", "Futni pagën"},
            {"EndDatePrompt", "Specifikoni përfundimin e datës së kontratës"},
            {"PositionPrompt", "Zgjidhni një pozicion"},
            {"StatusPrompt", "Zgjidh statusin"},
            {"OrgPrompt", "Shkruani emrin e organizatës"},
            {"OrgTypePrompt", "Zgjidhni llojin e organizatës"},
            {"AnnTurnoverPrompt", "Shkruani të ardhurat vjetore"},
            {"StreetPrompt", "Shkruani në rrugë"},
            {"PostalCodePrompt", "Shkruani indeksin"},
            {"ObjectNotChosen", "Së pari zgjidhni një objekt!"},
            {"WrongEnteredData", "Hynë të dhëna të pasakta!"},
            {"WorkerDisplay", "Emri: {0}\n" +
                    "Paga: {1}\n" +
                    "Data e përfundimit të kontratës: {2}\n" +
                    "Pozicioni: {3}\n" +
                    "Statusi: {4}\n" +
                    "Organizimi: {5}\n" +
                    "Një lloj: {6}"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
