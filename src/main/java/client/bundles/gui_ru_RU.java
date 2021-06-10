package client.bundles;

import java.util.ListResourceBundle;

public class gui_ru_RU extends ListResourceBundle {
    private static final Object[][] contents = {
            {"AddError", "Ошибка добавления элемента"},
            {"AddSuccess", "Элемент успешно добавлен"},
            {"CommandError", "Ошибка при обращении к серверу"},
            {"AddIfMinError", "Ошибка добавления минимального элемента"},
            {"AddIfMinSuccess", "Минимальный элемент успешно добавлен"},
            {"CountByStatusMessage", "Элементов с таким же статусом:"},
            {"ScriptSuccess", "Скрипт выполнен успешно"},
            {"ScriptError", "Во время выполнения скрипта произошла ошибка"},
            {"ExitMessage", "До новых встреч"},
            {"HelpMessage", "Справка:\n" +
                    "В нижней части интерфейса расположены кнопки взаимодействия с базой. Для выполнения действий, меняющих содержимое БД, необходимо сначала выбрать ряд таблицы. Вы также можете кликнуть дважды по необходимому ряду таблицы, чтобы изменить объект.\n" +
                    "Команда \"Очистить\" удаляет из БД лишь те объекты, что принадлежат вам.\n" +
                    "Вы можете перейти в раздел \"Визуализация\", чтобы увидеть визуальное представление объектов БД. Двойной клик по объекту даст возможность его отредактировать."},
            {"InfoMessage", "Информация о коллекции: \n Дата доступа: {0} \n Тип: {1} \n Размер: {2}"},
            {"LoginSuccess", "Вход успешен!"},
            {"LoginError", "Неверные данные для входа! "},
            {"PrintOrgsMessage", "Уникальные организации:"},
            {"RegisterSuccess", "Регистрация успешна!"},
            {"RegisterError", "Ошибка при регистрации, попробуйте позже."},
            {"RemoveSuccess", "Элемент удален успешно!"},
            {"RemoveError", "Ошибка при удалении элемента."},
            {"RemoveGreaterSuccess", "Большие элементы удалены."},
            {"RemoveGreaterError", "Ошибка при удалении больших элементов. "},
            {"RemoveLowerSuccess", "Меньшие элементы удалены."},
            {"RemoveLowerError", "Ошибка при удалении меньших элементов."},
            {"SaveMessage", "Коллекция сохранена в файл."},
            {"Empty", "Кхм"},
            {"UpdateSuccess", "Изменение объекта успешно!"},
            {"UpdateError", "Ошибка при изменении объекта."},
            {"IdColumn", "Идентификатор"},
            {"NameColumn", "Имя"},
            {"XColumn", "X"},
            {"YColumn", "Y"},
            {"SalaryColumn", "Оклад"},
            {"EndDateColumn", "Дата расторжения контракта"},
            {"CreationDateColumn", "Дата добавления в базу"},
            {"PositionColumn", "Должность"},
            {"StatusColumn", "Статус"},
            {"OrgColumn", "Организация"},
            {"OrgTypeColumn", "Тип организации"},
            {"AnnualTurnoverColumn", "Годовая выручка"},
            {"StreetColumn", "Улица"},
            {"PostalCodeColumn", "Индекс"},
            {"UserColumn", "Пользователь"},
            {"RefreshButton", "Обновить"},
            {"AddButton", "Добавить"},
            {"UpdateButton", "Изменить"},
            {"RemoveButton", "Удалить"},
            {"RemoveGreaterButton", "Удалить большие"},
            {"RemoveLowerButton", "Удалить меньшие"},
            {"ClearButton", "Очистить"},
            {"HelpButton", "Помощь"},
            {"InfoButton", "Информация"},
            {"CountByStatusButton", "Посчитать по статусу"},
            {"PrintUniqueOrgsButton", "Уникальные организации"},
            {"SaveButton", "Сохранить в файл"},
            {"AddIfMinButton", "Добавить если меньший"},
            {"ExecuteScriptButton", "Выполнить скрипт"},
            {"VisualMapTab", "Визуализация"},
            {"DataTableTab", "Таблица"},
            {"PortUnavailableError", "Сервер в данный момент недоступен."},
            {"ConnectionError", "Подключение к серверу не удалось."},
            {"AddressError", "Адрес подключения не найден."},
            {"FatalConnectionError", "Наташ, ты спишь? Уже 6 часов утра, Наташ. Вставай, мы там всё уронили. Мы уронили вообще всё, Наташ, честно."},
            {"LoginLabel", "Авторизация"},
            {"LoginFieldText", "Введите логин"},
            {"PwdFieldText", "Введите пароль"},
            {"LoginButton", "Войти в систему"},
            {"RegisterButton", "Зарегистрироваться"},
            {"TryLater", "Попробуйте выполнить это действие позже"},
            {"DoneButton", "Готово"},
            {"NameLabel", "Имя"},
            {"XLabel", "X"},
            {"YLabel", "Y"},
            {"SalaryLabel", "Оклад"},
            {"EndDateLabel", "Дата расторжения контракта"},
            {"PositionLabel", "Должность"},
            {"StatusLabel", "Статус"},
            {"OrgLabel", "Организация"},
            {"OrgTypeLabel", "Тип организации"},
            {"AnnTurnoverLabel", "Годовая выручка"},
            {"StreetLabel", "Улица"},
            {"PostalCodeLabel", "Индекс"},
            {"NamePrompt", "Введите имя"},
            {"XPrompt", "X"},
            {"YPrompt", "Y"},
            {"SalaryPrompt", "Введите оклад"},
            {"EndDatePrompt", "Укажите дату расторжения контракта"},
            {"PositionPrompt", "Выберите должность"},
            {"StatusPrompt", "Выберите статус"},
            {"OrgPrompt", "Введите название организации"},
            {"OrgTypePrompt", "Выберите тип организации"},
            {"AnnTurnoverPrompt", "Введите годовую выручку"},
            {"StreetPrompt", "Введите улицу"},
            {"PostalCodePrompt", "Введите индекс"},
            {"ObjectNotChosen", "Сначала выберите объект!"},
            {"WrongEnteredData", "Введены некорректные данные!"},
            {"WorkerDisplay", "Имя: {0}\n" +
                    "Оклад: {1}\n" +
                    "Дата конца контракта: {2}\n" +
                    "Должность: {3}\n" +
                    "Статус: {4} \n " +
                    "Организация:  {5}\n" +
                    "Тип: {6}"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
