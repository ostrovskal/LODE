package ru.ostrovskal.lode;

import android.graphics.Color;
import android.view.Gravity;

import static com.github.ostrovskal.ssh.Constants.SEEK_ANIM_ROTATE;
import static com.github.ostrovskal.ssh.Constants.SEEK_ANIM_SCALE;
import static com.github.ostrovskal.ssh.Constants.TILE_GRAVITY_CENTER;
import static com.github.ostrovskal.ssh.Constants.TILE_SCALE_MIN;
import static com.github.ostrovskal.ssh.Constants.TILE_SHAPE_ROUND;
import static com.github.ostrovskal.ssh.Constants.TILE_STATE_HOVER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_BACKGROUND;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_CLICKABLE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_COLOR_DEFAULT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_FOCUSABLE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_FONT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_GRAVITY;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_INT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_PADDING;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SHADOW_COLOR;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SHADOW_DX;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SHADOW_DY;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SHADOW_RADIUS;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SIZE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_ALIGNED;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BITMAP_NAME;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_BACKGROUND;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_BUTTONS;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_CHECK;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_EDIT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_HEADER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_ICONS;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_MENU;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_RADIO;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_SEEK;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_SELECT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_SWITCH;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_TILES;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_BM_TOOLS;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_DIVIDER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_HEADER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_HINT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_HTML_HEADER;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_LARGE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_LAYOUT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_LINK;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_MESSAGE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_NORMAL;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_SELECTOR;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_SMALL;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_WINDOW;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_COLOR_WIRED;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_GRAVITY;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_HORZ;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_MODE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_RADII;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_SCALE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_SHAPE;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_SPACING;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_STATES;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_THEME_NAME;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_VERT;
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_SSH_WIDTH_SELECTOR;
import static com.github.ostrovskal.ssh.StylesAndAttrs.COLOR;
import static com.github.ostrovskal.ssh.StylesAndAttrs.THEME;

public final class Constants
{
    public static final String lodeController        =  "ULULUUUUUUUUURUR\n" +
                                                        "ULULUUUUUUUUURUR\n" +
                                                        "LLLLULUUUUURRRRR\n" +
                                                        "LLLLLLFFFFRRRRRR\n" +
                                                        "LLLLLLFFFFRRRRRR\n" +
                                                        "LLLLDLDDDDDRRRRR\n" +
                                                        "DLDLDDDDDDDDDRDR\n" +
                                                        "DLDLDDDDDDDDDRDR";

    // Признак движения влево
    public final static int MODE_LEFT               = 0x100;
    // Признак движения вправо
    public final static int MODE_RIGHT              = 0x200;
    // Признак движения по лестнице
    public final static int MODE_TRAP               = 0x20;
    // Признак падения
    public final static int MODE_DROP               = 0x40;
    // Признак толкания
    //public final static int MODE_DRAG               = 0x80;

    // режимы сдвига
    public static final int SHIFT_UNDEF            = 0;
    public static final int SHIFT_MAP              = 1;
    public static final int SHIFT_TILE             = 2;

    // операции с массивом
    public static final int OPS_SET                 = 0;
    public static final int OPS_OR                  = 1;
    public static final int OPS_AND                 = 2;
    public static final int OPS_HORZ                = 3;
    public static final int OPS_VERT                = 4;
    public static final int OPS_FULL                = 5;

    // Базовые
    public static final String PACK_DEFAULT    = "classic";
    public final static int    BLOCK_MINIATURE = 8;
    //public final static int    LIMIT_RECORDS   = 50;
    public final static int    STD_GAME_SPEED  = 50;
    public final static int    SEGMENTS        = 4;
    public final static int    PERSON_LIFE     = 5;
    public final static int    PERSON_ADD_LIMIT= 1000;
    // задержка между сменой статуса
    public static final long MESSAGE_DELAYED   = 1000;
    // задержка сплэш экрана
    public static final long SPLASH_DELAYED    = 5000;

    // индексы звуков
    public static final int SND_PERSON_TAKE  = 0;
    public static final int SND_VOLUME       = 1;
    public static final int SND_PERSON_DESTR = 2;
    public static final int SND_PERSON_DEAD  = 3;
    public static final int SND_LEVEL_CLEAR  = 4;
    public static final int SND_LEVEL_NEXT   = 5;
    public static final int SND_ENEMY_DEAD   = 6;

    // индексы параметров планеты
    public static final int PARAM_SCORE    = 0;
    public static final int PARAM_LIFE     = 1;
    public static final int PARAM_GOLD     = 2;
    public static final int PARAM_LEVEL    = 3;
    public static final int PARAM_LIMIT    = 4;
    public static final int PARAMS_COUNT   = 5;

    // Действия
    public static final int ACTION_LOAD     = 10000; // загрузка уровня
    public static final int ACTION_SAVE     = 10001; // сохранение уровня
    public static final int ACTION_DELETE   = 10002; // удаление уровня
    public static final int ACTION_NEW      = 10003; // новый уровень
    public static final int ACTION_GENERATE = 10004; // генерация классических уровней
    public static final int ACTION_PACK     = 10005; // новый пакет
    public static final int ACTION_FINISH   = 10006; // последний уровень системы
    public static final int ACTION_NAME     = 10007; // установка имени уровня в редаторе
    public static final int ACTION_PROP     = 10008; // изменение свойств уровня

    // Состояние игры
    public static final int STATUS_INIT     = 1; // инициализация
    public static final int STATUS_DEAD     = 2; // игрок мертв
    public static final int STATUS_CLEARED  = 3; // уровень пройден
    public static final int STATUS_PREPARED = 4; // установка значений
    public static final int STATUS_LOOP     = 5; // игровой цикл
    public static final int STATUS_MESSAGE  = 6; // сообщение
    public static final int STATUS_EXIT     = 7; // выход
    public static final int STATUS_UNK      = 8; // неопределено
    public static final int STATUS_SUICIDED = 9; // самоубийство

    // формы
    public final static int FORM_GAME           = 0;
    //public final static int FORM_CHOICE         = 1;
    //public final static int FORM_OPTIONS        = 2;
    //public final static int FORM_EDITOR         = 3;
    public final static int FORM_GAME_HELP      = 4;
    public final static int FORM_DLG_EXIT       = 5;
    //public final static int FORM_RECORDS        = 6;
    public final static int FORM_SPLASH         = 7;
    public final static int FORM_MENU           = 8;
    public final static int FORM_DLG_E_ACTIONS  = 9;
    public final static int FORM_LEVEL_OPEN     = 10;
    public final static int FORM_LEVEL_NEW      = 11;
    //public final static int FORM_LEVEL_PROP     = 12;
    public final static int FORM_DLG_DELETE     = 13;
    public final static int FORM_DLG_GENERATE   = 14;
    public final static int FORM_DLG_SAVE       = 15;
    //public final static int FORM_SEND           = 16;
    public final static int FORM_RECV           = 17;
    public final static int FORM_FINISH         = 18;
    public final static int FORM_EDITOR_HELP    = 19;
    public final static int FORM_DLG_NEW_PACK   = 20;
    public final static int FORM_DLG_G_ACTIONS  = 21;

    // дествия формы операций в редакторе планет
    //public static final int    FORM_CHOICE_OPEN    = 1;
    //public static final int    FORM_CHOICE_NEW     = 2;
    public static final int    FORM_CHOICE_PROP    = 3;
    public static final int    FORM_CHOICE_DEL     = 4;
    //public static final int    FORM_CHOICE_GEN     = 5;
    public static final int    FORM_CHOICE_SAVE    = 6;
    public static final int    FORM_CHOICE_SEND    = 7;
    public static final int    FORM_CHOICE_PREV    = 8;
    public static final int    FORM_CHOICE_TEST    = 9;
    //public static final int    FORM_CHOICE_HELP    = 10;

    // ключи установок
    public final static String KEY_SCALE        = "scale";
    public final static String KEY_SPEED        = "speed";
    public final static String KEY_MUSIC        = "music";
    public final static String KEY_SOUND        = "sound";
    public final static String KEY_MASTER       = "master";
    public final static String KEY_AUTHOR       = "author";
    public final static String KEY_AUTHOR_COUNT = "#author_count";
    public final static String KEY_SCALE_VOLUME = "scale_volume";
    public final static String KEY_SPEED_VOLUME = "speed_volume";
    public final static String KEY_MUSIC_VOLUME = "music_volume";
    public final static String KEY_SOUND_VOLUME = "sound_volume";
    public final static String KEY_THEME        = "theme";
    public final static String KEY_TMP_THEME    = "#tmp_theme";
    public final static String KEY_PLAYER       = "player";
    public final static String KEY_PACK         = "#pack";
    public final static String KEY_FIRST_START  = "first_start";
    public final static String KEY_EDIT_TILE    = "#edit_tile";
    public final static String KEY_EDIT_LEVEL   = "#edit_level";
    public final static String KEY_EDIT_PREVIEW = "#edit_preview";
    public final static String KEY_TYPE_LEVEL   = "#type_level";

    // Типы тайлов
    public final static byte T_BETON            = 0;
    public final static byte T_WALL             = 1;
    public final static byte T_TRAP             = 2;
    private final static byte T_GOLD            = 3;
    private final static byte T_SCORE           = 4;
    public final static byte T_TRAPN            = 5;
    private final static byte T_WALLN           = 6;
    public final static byte T_BUTTON           = 7;
    public final static byte T_DESTR0           = 8;
    private final static byte T_DESTR1          = 9;
    private final static byte T_DESTR2          = 10;
    private final static byte T_DESTR3          = 11;
    public final static byte T_NULL             = 12;
    private final static byte T_ZARAST0         = 13;
    private final static byte T_ZARAST1         = 14;
    private final static byte T_ZARAST2         = 15;
    private final static byte T_ZARAST3         = 16;
    public final static byte T_BOX              = 17;
    public final static byte T_FIRE0            = 18;
    private final static byte T_FIRE1           = 19;
    private final static byte T_FIRE2           = 20;
    private final static byte T_FIRE3           = 21;
    private final static byte T_FIRE4           = 22;
    private final static byte T_FIRE5           = 23;
    private final static byte T_FIRE6           = 24;
    private final static byte T_FIRE7           = 25;
    private final static byte T_LAVA0           = 26;
    private final static byte T_LAVA1           = 27;
    private final static byte T_LAVA2           = 28;
    private final static byte T_LAVA3           = 29;
    public final static byte T_BUTTON_PRESS     = 30;
    private final static byte T_BRIDGE          = 31;
    private final static byte T_PLATH           = 32;
    private final static byte T_PLATV           = 33;
    public final static byte T_POLZ_H_BEGIN     = 34;
    public final static byte T_POLZ_H_MIDDLE    = 35;
    public final static byte T_POLZ_H_END       = 36;
    /*public final static byte T_POLZ_V_BEGIN     = 37;*/
    private final static byte T_POLZ_V_MIDDLE   = 38;
    /*public final static byte T_POLZ_V_END       = 39;*/

    public final static byte T_ENEMY1_DROP      = 40;
    public final static byte T_ENEMY2_DROP      = 53;
    public final static byte T_PERSON_DROP      = 66;

    public final static byte T_BUBBLE0          = 79;

    public final static byte T_BRIDGE0          = 90;
    private final static byte T_BRIDGE1         = 91;
    private final static byte T_BRIDGE2         = 92;
    private final static byte T_BRIDGE3         = 93;

    // Маски тайлов
    public final static int MSKO                = 0x1f;
    public final static int MSKT                = 0x1f;
    public final static int MSKP                = 0x3f;
    public final static int MSKE                = 0x20;
    public final static int MSKZ                = 0x40;

    // Флаги тайлов
    public final static int FA                  = 0x020;// Все гибнут
    public final static int FP                  = 0x040;// Человек гибнет
    public final static int FD                  = 0x080;// Все падают
    public final static int FN                  = 0x100;// Проходимый
    public final static int FT                  = 0x200;// Лестница
    public final static int FL                  = 0x400;// Переменная длина
    public final static int FB                  = 0x800;// Ползунок/Платформа не проходит
    public final static int FE                  = 0x1000;// Enemy на чем то
    public final static int FF                  = 0x2000;// Можно прорывать
    public final static int FZ                  = 0x4000;// Ползунок/Платформа

    // Типы объектов
    public final static int O_BETON  = 0;
    public final static int O_WALL   = 1;
    public final static int O_TRAP   = 2;
    public final static int O_GOLD   = 3;
    public final static int O_SCORE  = 4;
    public final static int O_TRAPN  = 5;
    public final static int O_WALLN  = 6;
    public final static int O_BUTTON = 7;
    public final static int O_DESTR  = 8;
    private final static int O_UNDEF = 9;
    public final static int O_BUBBLE = 10;
    public final static int O_NULL   = 12;
    public final static int O_ZARAST = 13;
    public final static int O_FIRE   = 17;
    public final static int O_BOX    = 18;
    public final static int O_BRIDGE = 19;
    public final static int O_PLATH  = 20;
    public final static int O_PLATV  = 21;
    public final static int O_POLZH  = 22;
    public final static int O_POLZV  = 23;
    public final static int O_ENEMY1 = 24;
    public final static int O_ENEMY2 = 25;
    public final static int O_PERSON = 26;

    // Массив значений для добавления очков
    public final static int valuesAddScores[] = {100, 0, 0, 50, 500, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 80, 30, 70, 0, 0, 0, 0, 150, 300, 0 };

    // Тайлы при генерации уровня
    public final static byte tileGenLevel[]     = { O_NULL, O_WALL, O_GOLD, O_TRAP};

    public final static byte showBridgeTiles[]  = { T_BRIDGE0, T_BRIDGE1, T_BRIDGE2, T_BRIDGE3, T_WALL};

    public final static byte showFireTiles[]  = { T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3, T_FIRE4, T_FIRE5, T_FIRE6, T_FIRE7,
                                                  T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3, T_FIRE4, T_FIRE5, T_FIRE6, T_FIRE7};

    public final static byte showLavaTiles[]  = { T_LAVA0, T_LAVA1, T_LAVA2, T_LAVA3, T_LAVA0, T_LAVA1, T_LAVA2, T_LAVA3};

    // Массив перекодировки тайлов в игре
    public final static int remapGameTiles[]    = {// 22
                                                   T_BETON, T_WALL, T_TRAP, T_GOLD, T_SCORE, T_NULL, T_WALL, T_NULL, T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3,
                                                   T_NULL, T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3, T_FIRE3, T_BOX, T_BRIDGE, T_PLATH, T_PLATV,
                                                   T_POLZ_H_MIDDLE, T_POLZ_V_MIDDLE, T_ENEMY1_DROP, T_ENEMY2_DROP, T_PERSON_DROP};

    // Массив перекодировки тайлов в редакторе
    public final static int remapEditorTiles[]   = {T_BETON, T_WALL, T_TRAP, T_GOLD, T_SCORE, T_TRAPN, T_WALLN, T_BUTTON,
                                                    T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3, T_NULL, T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3, T_FIRE0,
                                                    T_BOX, T_BRIDGE, T_PLATH, T_PLATV, T_POLZ_H_MIDDLE, T_POLZ_V_MIDDLE, T_ENEMY1_DROP, T_ENEMY2_DROP, T_PERSON_DROP};

    public static final int[] tilesEditorPanel  = {R.integer.TILE_NULL, R.integer.TILE_WALL, R.integer.TILE_BETON, R.integer.TILE_TRAP, R.integer.TILE_TRAPN,
                                                   R.integer.TILE_GOLD, R.integer.TILE_BUTTON, R.integer.TILE_WALLN, R.integer.TILE_SCORE,
                                                   R.integer.TILE_ENEMY1_DROP, R.integer.TILE_ENEMY2_DROP, R.integer.TILE_PERSON_DROP, R.integer.TILE_PLATH,
                                                   R.integer.TILE_PLATV, R.integer.TILE_POLZH_MIDDLE, R.integer.TILE_POLZV_MIDDLE, R.integer.TILE_FIRE0,
                                                   R.integer.TILE_BOX, R.integer.TILE_BRIDGE, R.integer.TILE_NULL};

    public static final int[] iconsEditorActions= {R.integer.I_OPEN_LEVEL, R.integer.I_NEW_LEVEL, R.integer.I_PROP_LEVEL, R.integer.I_DELETE_LEVEL, R.integer.I_BLOCKED, R.integer.I_SAVE_LEVEL, R.integer.I_SEND_PACK, R.integer.I_PREVIEW_OFF, R.integer.I_TEST_LEVEL, R.integer.I_HELP};

    // Массив перекодировки номеров тайлов в символы при сохранении
    public final static char charsOfLevelMap[]  = {// 27 BETON, WALL, TRAP, GOLD, SCORE, TRAPN, WALLN, BUTTON, DESTR0-3,NULL,ZARAST0-3,
                                                   'B', 'W', 'H', 'x', 'S', 'h', 'N', 'b', '?', '?', '?', '?', ' ', '?', '?', '?', '?',
                                                   // FIRE, BOX, BRIDGE, PLATH, PLATV, POLZH, POLZV, ENEMY1, ENEMY2, PERSON
                                                   'F', 'O', '*', '=', '+', '-', '|', '1', '2', 'M'};

    // Массив характеристик каждого тайла
    public final static int remapProp[]         = {
                                                   // T_BETON, T_WALL, T_TRAP, T_GOLD
                                                   FA | FP | FL | FB, O_WALL | FA | FP | FL | FB, O_TRAP | FT | FN | FL | FB, O_GOLD | FN | FD | FL | FB,
                                                   // T_SCORE, T_TRAPN, T_WALLN, T_BUTTON
                                                   O_SCORE | FN | FD | FL | FB, O_TRAPN | FD | FN | FF, O_WALLN | FD | FN | FL | FF, O_BUTTON | FB,
                                                   // T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3
                                                   O_DESTR | FN | FD, O_DESTR | FN | FD, O_DESTR | FN | FD, O_DESTR | FN | FD,
                                                   // NULL
                                                   O_NULL | FD | FN | FL | FF,
                                                   // T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3
                                                   O_ZARAST | FN | FD | FB, O_ZARAST | FN | FD | FB, O_ZARAST | FA | FP | FB | FD, O_ZARAST | FA | FP | FB | FD,
                                                   // T_FIRE0,
                                                   O_FIRE | FN | FA | FD | FP | FL,
                                                   // T_BOX
                                                   O_BOX | FA | FP | FF | FB,
                                                   // T_BRIDGE, T_PLATH, T_PLATV, T_POLZ_H_MIDDLE, T_POLZ_V_MIDDLE
                                                   O_BRIDGE | FA | FP | FB | FL, O_PLATH | FL, O_PLATV | FL, O_POLZH | FL, O_POLZV | FL,
                                                   // T_ENEMY1_DROP, T_ENEMY2_DROP, T_PERSON_DROP
                                                   O_ENEMY1, O_ENEMY2, O_PERSON,
                                                   // SKIP
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,

                                                   // 32 (MSKE)
                                                   // T_E_BETON, T_E_WALL, T_E_TRAP, T_E_GOLD
                                                   FA | FP | FB, O_WALL | FA | FP | FB, O_TRAP | FE | FT | FN | FB, O_GOLD | FE | FN | FD | FB,
                                                   // T_E_SCORE, T_E_TRAPN, T_E_WALLN, T_E_BUTTON
                                                   O_SCORE | FE | FP | FB, O_TRAPN | FE | FF | FP, O_WALLN | FE | FF | FP, O_BUTTON | FA | FP | FB,
                                                   // T_E_DESTR0, T_E_DESTR1, T_E_DESTR2, T_E_DESTR3
                                                   O_DESTR | FE | FF | FP, O_DESTR | FE | FF | FP, O_DESTR | FE | FF | FP, O_DESTR | FE | FF | FP,
                                                   // T_E_NULL
                                                   O_NULL | FE | FF | FP,
                                                   // T_E_ZARAST0, T_E_ZARAST1, T_E_ZARAST2, T_E_ZARAST3
                                                   O_ZARAST | FE | FP | FB, O_ZARAST | FE | FP | FB, O_ZARAST | FE | FP | FA | FB, O_ZARAST | FE | FA | FP | FB,
                                                   // T_E_FIRE0,
                                                   O_FIRE | FE | FA | FP,
                                                   // T_E_BOX
                                                   O_BOX | FA | FP | FF | FB,
                                                   // T_E_BRIDGE
                                                   O_BRIDGE | FE | FA | FP | FB,
                                                   // SKIP
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,

                                                   // 64 (MSKZ)
                                                   // T_Z_BETON, T_Z_WALL, T_Z_TRAP, T_Z_GOLD
                                                   O_UNDEF, O_UNDEF, O_TRAP | FZ | FA | FF | FB, O_UNDEF,
                                                   // T_Z_SCORE, T_Z_TRAPN, T_Z_WALLN, T_Z_BUTTON
                                                   O_UNDEF, O_TRAPN | FZ | FA | FP | FB, O_WALLN | FZ | FA | FP | FB, O_UNDEF,
                                                   // T_Z_DESTR0, T_E_DESTR1, T_E_DESTR2, T_E_DESTR3
                                                   O_DESTR | FZ | FA | FP | FB, O_DESTR | FZ | FA | FP | FB, O_DESTR | FE | FA | FP | FB, O_DESTR | FE | FA | FP | FB,
                                                   // T_Z_NULL
                                                   O_NULL | FZ | FA | FP | FF | FB,
                                                   // T_Z_ZARAST0, T_Z_ZARAST1, T_Z_ZARAST2, T_Z_ZARAST3
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_E_FIRE0,
                                                   O_FIRE | FZ | FA | FP | FB,
                                                   // T_E_BOX
                                                   O_UNDEF,
                                                   // SKIP
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,

                                                   // 96 (MSKE MSKZ)
                                                   // T_ZE_BETON, T_ZE_WALL, T_ZE_TRAP, T_ZE_GOLD
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_E_SCORE, T_E_TRAPN, T_E_WALLN, T_ZE_BUTTON
                                                   O_UNDEF, O_TRAPN | FZ | FE | FA | FP | FB, O_WALLN | FZ | FE | FA | FP | FB, O_UNDEF,
                                                   // T_ZE_DESTR0, T_ZE_DESTR1, T_ZE_DESTR2, T_ZE_DESTR3
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_Z_NULL
                                                   O_NULL | FZ | FA | FP | FB,
                                                   // T_E_ZARAST0, T_E_ZARAST1, T_E_ZARAST2, T_E_ZARAST3
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_E_FIRE0,
                                                   O_FIRE | FZ | FE | FA | FP | FB,
                                                   // T_E_BOX
                                                   O_UNDEF};

    public final static int tilesGamePanel[]        = {R.integer.TILE_PERSON_DROP, R.integer.TILE_GOLD, R.integer.TILE_WALL};

    // длины счетчиков
    public static final int[]  formatLengths       = {6, 3, 3, 3};

    private static final int ATTR_SSH_COLOR_PANEL_COUNTERS  = 1000 | ATTR_INT;
    private static final int ATTR_SSH_COLOR_STAT_COUNTERS   = 1002 | ATTR_INT;
    private static final int ATTR_SSH_COLOR_NUM_LEVEL       = 1003 | ATTR_INT;

    public static final int[] themeDark = {ATTR_SSH_SPACING, R.dimen.cellSpacing,
                                           ATTR_SSH_THEME_NAME, R.string.themeDark,
                                           ATTR_SSH_BM_MENU, R.drawable.menu,
                                           ATTR_SSH_BM_ICONS, R.drawable.icon_tiles,
                                           ATTR_SSH_MODE, SEEK_ANIM_ROTATE,
                                           ATTR_SSH_COLOR_DIVIDER, 0x7a7a7a | COLOR,
                                           ATTR_SSH_COLOR_LAYOUT, 0x2d2929 | COLOR,
                                           ATTR_SSH_COLOR_NORMAL, 0x9599f7 | COLOR,
                                           ATTR_SSH_COLOR_LARGE, 0xbc5a1d | COLOR,
                                           ATTR_SSH_COLOR_SMALL, 0x2ea362 | COLOR,
                                           ATTR_SSH_COLOR_HINT, 0xf77499 | COLOR,
                                           ATTR_SSH_COLOR_SELECTOR, Color.MAGENTA | COLOR,
                                           ATTR_SSH_COLOR_HEADER, 0xcfba41 | COLOR,
                                           ATTR_SSH_COLOR_HTML_HEADER, 0xf22782 | COLOR,
                                           ATTR_SSH_COLOR_PANEL_COUNTERS, 0xeac8c8 | COLOR,
                                           ATTR_SSH_COLOR_STAT_COUNTERS, 0xaafdfd | COLOR,
                                           ATTR_SSH_COLOR_NUM_LEVEL, 0xe7e114 | COLOR,
                                           ATTR_SSH_COLOR_LINK, 0xb8fa01 | COLOR,
                                           ATTR_SSH_COLOR_MESSAGE, 0xd2fa64 | COLOR,
                                           ATTR_SSH_COLOR_WINDOW, 0x030303 | COLOR,
                                           ATTR_SSH_COLOR_WIRED, 0x808080 | COLOR,
                                           ATTR_SSH_BM_BACKGROUND, R.drawable.theme_background_dark,
                                           ATTR_SSH_BM_HEADER, R.drawable.theme_header_dark,
                                           ATTR_SSH_BM_SELECT, R.drawable.theme_select_dark,
                                           ATTR_SSH_BM_EDIT, R.drawable.theme_edit_dark,
                                           ATTR_SSH_BM_TOOLS, R.drawable.theme_tool_dark,
                                           ATTR_SSH_BM_BUTTONS, R.drawable.theme_button_dark,
                                           ATTR_SSH_BM_RADIO, R.drawable.theme_radio_dark,
                                           ATTR_SSH_BM_CHECK, R.drawable.theme_check_dark,
                                           ATTR_SSH_BM_SEEK, R.drawable.theme_seek_dark,
                                           ATTR_SSH_BM_SWITCH, R.drawable.theme_switch_dark,
                                           ATTR_SSH_BM_TILES, R.drawable.sprites};

    public static final int[] themeLight = {ATTR_SSH_SPACING, R.dimen.cellSpacing,
                                            ATTR_SSH_THEME_NAME, R.string.themeLight,
                                            ATTR_SSH_BM_MENU, R.drawable.menu,
                                            ATTR_SSH_BM_ICONS, R.drawable.icon_tiles,
                                            ATTR_SSH_MODE, SEEK_ANIM_SCALE,
                                            ATTR_SSH_COLOR_DIVIDER, 0x7f7f7f | COLOR,
                                            ATTR_SSH_COLOR_LAYOUT, 0x9e5e1e | COLOR,
                                            ATTR_SSH_COLOR_NORMAL, 0xea5191 | COLOR,
                                            ATTR_SSH_COLOR_LARGE, 0x5670f1 | COLOR,
                                            ATTR_SSH_COLOR_SMALL, 0x9841df | COLOR,
                                            ATTR_SSH_COLOR_HINT, 0xf77499 | COLOR,
                                            ATTR_SSH_COLOR_SELECTOR, 0xf23346 | COLOR,
                                            ATTR_SSH_COLOR_HEADER, 0xa9f145 | COLOR,
                                            ATTR_SSH_COLOR_HTML_HEADER, 0x9a4dfc | COLOR,
                                            ATTR_SSH_COLOR_PANEL_COUNTERS, 0xb7a0a0 | COLOR,
                                            ATTR_SSH_COLOR_STAT_COUNTERS, 0xe36efa | COLOR,
                                            ATTR_SSH_COLOR_NUM_LEVEL, 0xf9f681 | COLOR,
                                            ATTR_SSH_COLOR_LINK, 0xb8fa01 | COLOR,
                                            ATTR_SSH_COLOR_MESSAGE, 0xd2fa64 | COLOR,
                                            ATTR_SSH_COLOR_WINDOW, 0x020202 | COLOR,
                                            ATTR_SSH_COLOR_WIRED, 0x404040 | COLOR,
                                            ATTR_SSH_BM_BACKGROUND, R.drawable.theme_background_light,
                                            ATTR_SSH_BM_HEADER, R.drawable.theme_header_light,
                                            ATTR_SSH_BM_SELECT, R.drawable.theme_select_light,
                                            ATTR_SSH_BM_EDIT, R.drawable.theme_edit_light,
                                            ATTR_SSH_BM_TOOLS, R.drawable.theme_tool_light,
                                            ATTR_SSH_BM_BUTTONS, R.drawable.theme_button_light,
                                            ATTR_SSH_BM_RADIO, R.drawable.theme_radio_light,
                                            ATTR_SSH_BM_CHECK, R.drawable.theme_check_light,
                                            ATTR_SSH_BM_SEEK, R.drawable.theme_seek_light,
                                            ATTR_SSH_BM_SWITCH, R.drawable.theme_switch_light,
                                            ATTR_SSH_BM_TILES, R.drawable.sprites};

    public static final int[] style_text_level = {ATTR_SHADOW_DX, R.dimen.shadowTextX,
                                                  ATTR_SHADOW_DY, R.dimen.shadowTextY,
                                                  ATTR_SHADOW_RADIUS, R.dimen.shadowTextR,
                                                  ATTR_SHADOW_COLOR, 0x1 | COLOR,
                                                  ATTR_GRAVITY, Gravity.CENTER,
                                                  ATTR_COLOR_DEFAULT, ATTR_SSH_COLOR_NUM_LEVEL | THEME,
                                                  ATTR_SIZE, R.dimen.level,
                                                  ATTR_FONT, R.string.font_large};

    public static final int[] style_tile_lode = {ATTR_SSH_VERT, 10,
                                                  ATTR_SSH_HORZ, 10,
                                                  ATTR_FOCUSABLE, 0,
                                                  ATTR_CLICKABLE, 1,
                                                  ATTR_SSH_WIDTH_SELECTOR, 2,
                                                  ATTR_BACKGROUND, ATTR_SSH_COLOR_SELECTOR | THEME,
                                                  ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_TILES | THEME};

    public static final int[] style_button_actions = {ATTR_SHADOW_DX, R.dimen.shadowTextX,
                                                      ATTR_SHADOW_DY, R.dimen.shadowTextY,
                                                      ATTR_SHADOW_RADIUS, R.dimen.shadowTextR,
                                                      ATTR_SHADOW_COLOR, 0x1 | COLOR,
                                                      ATTR_SIZE, R.dimen.large,
                                                      ATTR_FONT, R.string.font_large,
                                                      ATTR_COLOR_DEFAULT, ATTR_SSH_COLOR_MESSAGE | THEME,
                                                      ATTR_CLICKABLE, 1,
                                                      ATTR_PADDING, 2,
                                                      ATTR_SSH_ALIGNED, 1,
                                                      ATTR_SSH_HORZ, 2,
                                                      ATTR_SSH_STATES, TILE_STATE_HOVER,
                                                      ATTR_GRAVITY, Gravity.CENTER,
                                                      ATTR_SSH_GRAVITY, TILE_GRAVITY_CENTER,
                                                      ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_BUTTONS | THEME };

    public static final int[] style_text_finish = {ATTR_SHADOW_DX, R.dimen.shadowTextX,
                                                   ATTR_SHADOW_DY, R.dimen.shadowTextY,
                                                   ATTR_SHADOW_RADIUS, R.dimen.shadowTextR,
                                                   ATTR_SHADOW_COLOR, 0x1 | COLOR,
                                                   ATTR_GRAVITY, Gravity.CENTER_VERTICAL,
                                                   ATTR_COLOR_DEFAULT, ATTR_SSH_COLOR_NORMAL | THEME,
                                                   ATTR_SIZE, R.dimen.finish,
                                                   ATTR_FONT, R.string.font_large};

    public static final int[] style_text_counters = {ATTR_SHADOW_DX, R.dimen.shadowTextX,
                                                     ATTR_SHADOW_DY, R.dimen.shadowTextY,
                                                     ATTR_SHADOW_RADIUS, R.dimen.shadowTextR,
                                                     ATTR_SHADOW_COLOR, 0x1 | COLOR,
                                                     ATTR_GRAVITY, Gravity.CENTER_VERTICAL,
                                                     ATTR_COLOR_DEFAULT, ATTR_SSH_COLOR_PANEL_COUNTERS | THEME,
                                                     ATTR_SIZE, R.dimen.panel,
                                                     ATTR_FONT, R.string.font_normal};

    public static final int[] style_panel_tile =  {ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_TILES | THEME,
                                                   ATTR_SSH_HORZ, 10,
                                                   ATTR_SSH_VERT, 10,
                                                   ATTR_CLICKABLE, 0,
                                                   ATTR_FOCUSABLE, 0,
                                                   ATTR_SSH_ALIGNED, 1,
                                                   ATTR_SSH_SCALE, TILE_SCALE_MIN,
                                                   ATTR_SSH_GRAVITY, Gravity.CENTER};

    public static final int[] style_dlg_actions = {ATTR_SSH_SHAPE, TILE_SHAPE_ROUND,
                                                   ATTR_SSH_RADII, R.string.radii_dlg};

    public static final int[] style_panel_h      = {ATTR_SSH_BITMAP_NAME, R.drawable.panel_horz};
    public static final int[] style_panel_v      = {ATTR_SSH_BITMAP_NAME, R.drawable.panel_vert};
}
