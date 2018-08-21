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
import static com.github.ostrovskal.ssh.StylesAndAttrs.ATTR_DRW;
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

    // режимы сдвига
    public static final int SHIFT_UNDEF            = 0;
    public static final int SHIFT_MAP              = 1;
    public static final int SHIFT_TILE             = 2;

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
    public static final int SND_PERSON_TAKE     = 0;
    public static final int SND_VOLUME          = 1;
    public static final int SND_PERSON_DESTR    = 2;
    public static final int SND_PERSON_DEAD     = 3;
    public static final int SND_LEVEL_CLEAR     = 4;
    public static final int SND_LEVEL_NEXT      = 5;
    public static final int SND_OBJECT_TO_FIRE  = 6;
    public static final int SND_PERSON_SCORE    = 7;

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
    public static final int ACTION_NUM      = 10007; // установка номера уровня в редаторе

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
    public final static int FORM_LEVEL_PROP     = 12;
    public final static int FORM_DLG_DELETE     = 13;
    public final static int FORM_DLG_GENERATE   = 14;
    public final static int FORM_DLG_SAVE       = 15;
    public final static int FORM_SEND           = 16;
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
    public final static byte T_GOLD             = 3;
    public final static byte T_BUTTON1          = 4;
    public final static byte T_FIRE0            = 5;
    public final static byte T_FIRE1            = 6;
    public final static byte T_FIRE2            = 7;
    public final static byte T_FIRE3            = 8;
    public final static byte T_WALLN            = 9;
    public final static byte T_DESTR0           = 10;
    public final static byte T_DESTR1           = 11;
    public final static byte T_DESTR2           = 12;
    public final static byte T_DESTR3           = 13;
    public final static byte T_NULL             = 14;
    public final static byte T_ZARAST0          = 15;
    public final static byte T_ZARAST1          = 16;
    public final static byte T_ZARAST2          = 17;
    public final static byte T_ZARAST3          = 18;
    public final static byte T_BUTTON0          = 19;
    public final static byte T_SCORE            = 20;
    public final static byte T_TRAPN            = 21;
    public final static byte T_PLATH            = 22;
    public final static byte T_PLATV            = 23;
    public final static byte T_POLZ_H_BEGIN     = 24;
    public final static byte T_POLZ_H_MIDDLE    = 25;
    public final static byte T_POLZ_H_END       = 26;
    //public final static byte T_POLZ_V_BEGIN     = 27;
    public final static byte T_POLZ_V_MIDDLE    = 28;
    //public final static byte T_POLZ_V_END       = 29;

    public final static byte T_ENEMY1_DROP      = 30;
    public final static byte T_ENEMY2_DROP      = 43;
    public final static byte T_PERSON_DROP      = 56;

    public final static byte T_BUBBLE0          = 50;

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
    public final static int O_BUBBLE = 4;
    public final static int O_FIRE   = 5;
    public final static int O_WALLN  = 9;
    public final static int O_ZARAST = 10;
    public final static int O_DESTR  = 11;
    private final static int O_UNDEF = 12;
    public final static int O_NULL   = 14;
    public final static int O_BUTTON = 19;
    public final static int O_SCORE  = 20;
    private final static int O_TRAPN = 21;
    public final static int O_PLATH  = 22;
    public final static int O_PLATV  = 23;
    public final static int O_POLZH  = 24;
    public final static int O_POLZV  = 25;
    public final static int O_ENEMY1 = 26;
    public final static int O_ENEMY2 = 27;
    public final static int O_PERSON = 28;

    // Массив значений для добавления очков
    public final static int valuesAddScores[] = {100, 0, 0, 50, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 500, 0, 0, 0, 0, 0, 150, 300, 0 };

    // Массив перекодировки тайлов
    public final static int remapTiles[]        = {// 22
                                                   T_BETON, T_WALL, T_TRAP, T_GOLD, T_BUTTON1, T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3, T_WALL,
                                                   T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3, T_NULL, T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3, T_WALL,
                                                   T_SCORE, T_NULL,
                                                   T_PLATH, T_PLATV, T_POLZ_H_MIDDLE, T_POLZ_V_MIDDLE, T_ENEMY1_DROP,
                                                   T_ENEMY2_DROP, T_PERSON_DROP/*, T_BETON, T_BETON, T_BETON,
                                                   T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON,
                                                   T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON,
                                                   T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON, T_BETON,
                                                   T_BETON, T_BETON,
                                                   T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL,
                                                   T_WALL, T_WALL, T_WALL, T_TRAP, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL,
                                                   T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL,
                                                   T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL,
                                                   T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL,
                                                   T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL, T_WALL*/};

    // Массив перекодировки тайлов
    public final static int remapEditorTiles[]   = {T_BETON, T_WALL, T_TRAP, T_GOLD, T_BUTTON1, T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3, T_WALLN,
                                                    T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3, T_NULL, T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3, T_BUTTON0,
                                                    T_SCORE, T_TRAPN, T_PLATH, T_PLATV, T_POLZ_H_MIDDLE, T_POLZ_V_MIDDLE,
                                                    T_ENEMY1_DROP, T_ENEMY2_DROP, T_PERSON_DROP};

    public static final int[] tilesEditorPanel  = {R.integer.TILE_NULL, R.integer.TILE_WALL, R.integer.TILE_BETON, R.integer.TILE_TRAP, R.integer.TILE_TRAPN,
                                                   R.integer.TILE_GOLD, R.integer.TILE_BUTTON1, R.integer.TILE_WALLN, R.integer.TILE_SCORE,
                                                   R.integer.TILE_ENEMY1_DROP, R.integer.TILE_ENEMY2_DROP, R.integer.TILE_PERSON_DROP, R.integer.TILE_PLATH,
                                                   R.integer.TILE_PLATV, R.integer.TILE_POLZH_MIDDLE, R.integer.TILE_POLZV_MIDDLE, R.integer.TILE_FIRE0,
                                                   R.integer.TILE_NULL};

    public static final int[] iconsEditorActions= {R.integer.I_OPEN_LEVEL, R.integer.I_NEW_LEVEL, R.integer.I_PROP_LEVEL,
                                                   R.integer.I_DELETE_LEVEL, R.integer.I_BLOCKED, R.integer.I_SAVE_LEVEL,
                                                   R.integer.I_SEND_PACK, R.integer.I_PREVIEW_LEVEL, R.integer.I_TEST_LEVEL,
                                                   R.integer.I_HELP};

    // Массив перекодировки номеров тайлов в символы при сохранении
    public final static char charsOfLevelMap[]  = {// 28 BETON, WALL, TRAP, GOLD, BUTTON1, FIRE0,,,,WALLN,,,,,NULL,,,,,
                                                   'B', 'W', 'H', 'x', '?', 'F', 'F', 'F', 'F', 'N', '?', '?', '?', '?', ' ', '?', '?', '?', '?',
                                                   // BUTTON0, SCORE, TRAPN, PLATH, PLATV, POLZH, POLZV
                                                   'b', 'S', 'h', '=', '+', '-', '|',
                                                   // ENEMY1, ENEMY2, PERSON
                                                   '1', '2', 'M'};

    // Массив характеристик каждого тайла
    public final static int remapProp[]         = {// T_BETON, T_WALL, T_TRAP, T_GOLD
                                                   FA | FP | FL | FB, O_WALL | FA | FP | FL | FB, O_TRAP | FT | FN | FL | FB, O_GOLD | FN | FD | FL | FB,
                                                   // T_BUTTON1
                                                   O_BUTTON | FB,
                                                   // T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3
                                                   O_FIRE | FN | FA | FD | FP | FL, O_FIRE | FN | FA | FD | FP | FL,
                                                   O_FIRE | FN | FA | FD | FP | FL, O_FIRE | FN | FA | FD | FP | FL,
                                                   // T_WALLN
                                                   O_WALLN | FD | FN | FL | FF,
                                                   // T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3
                                                   O_DESTR | FN | FD, O_DESTR | FN | FD, O_DESTR | FN | FD, O_DESTR | FN | FD,
                                                   // NULL
                                                   O_NULL | FD | FN | FL | FF,
                                                   // T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3
                                                   O_ZARAST | FN | FD | FB, O_ZARAST | FN | FD | FB, O_ZARAST | FA | FP | FB | FD, O_ZARAST | FA | FP | FB | FD,
                                                   // T_BUTTON0, T_SCORE, T_TRAPN
                                                   O_BUTTON | FB, O_SCORE | FN | FD | FB, O_TRAPN | FD | FN,
                                                   // T_PLATH, T_PLATV
                                                   O_PLATH | FL, O_PLATV | FL,
                                                   // T_POLZ_H_MIDDLE, T_POLZ_V_MIDDLE
                                                   O_POLZH | FL, O_POLZV | FL,
                                                   // T_ENEMY1_DROP, T_ENEMY2_DROP, T_PERSON_DROP
                                                   O_ENEMY1, O_ENEMY2, O_PERSON,

                                                   // 29
                                                   O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_E_BETON, T_E_WALL, T_E_TRAP, T_E_GOLD
                                                   FE | FA | FP | FB, O_WALL | FE | FA | FP | FB, O_TRAP | FE | FP | FB, O_GOLD | FE | FP | FB,
                                                   // T_E_BUTTON1
                                                   O_BUTTON | FE | FP | FA | FB,
                                                   // T_E_FIRE0, T_E_FIRE1, T_E_FIRE2, T_E_FIRE3
                                                   O_FIRE | FE | FA | FP, O_FIRE | FE | FA | FP, O_FIRE | FE | FA | FP, O_FIRE | FE | FA | FP,
                                                   // T_E_WALLN
                                                   O_WALLN | FE | FF | FP,
                                                   // T_E_DESTR0, T_E_DESTR1, T_E_DESTR2, T_E_DESTR3
                                                   O_DESTR | FE | FP, O_DESTR | FE | FP, O_DESTR | FE | FP, O_DESTR | FE | FP,
                                                   // T_E_NULL
                                                   O_NULL | FE | FF | FP,
                                                   // T_E_ZARAST0, T_E_ZARAST1, T_E_ZARAST2, T_E_ZARAST3
                                                   O_ZARAST | FE | FP, O_ZARAST | FE | FP, O_ZARAST | FE | FA | FP | FB, O_ZARAST | FE | FA | FP | FB,
                                                   // T_E_BUTTON0, T_E_SCORE, T_E_TRAPN
                                                   O_BUTTON | FE | FA | FP | FB, O_SCORE | FE | FP | FB, O_TRAPN | FE | FP,

                                                   // 54
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_BETON, T_WALL, T_TRAP, T_GOLD, T_BUTTON1
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_FIRE0, T_FIRE1, T_FIRE2, T_FIRE3
                                                   O_FIRE | FA | FP | FB, O_FIRE | FA | FP | FB, O_FIRE | FA | FP | FB, O_FIRE | FA | FP | FB,
                                                   // T_Z_WALLN
                                                   O_WALLN | FA | FP | FB,
                                                   // T_DESTR0, T_DESTR1, T_DESTR2, T_DESTR3
                                                   O_DESTR | FA | FP | FB, O_DESTR | FA | FP | FB, O_DESTR | FA | FP | FB, O_DESTR | FA | FP | FB,
                                                   // NULL
                                                   O_NULL | FA | FP | FB,
                                                   // T_ZARAST0, T_ZARAST1, T_ZARAST2, T_ZARAST3, T_BUTTON0, T_SCORE
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_TRAPN
                                                   O_TRAP | FA | FP | FB,

                                                   // 86
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_EZ_BETON, T_EZ_WALL, T_EZ_TRAP, T_EZ_GOLD, T_EZ_BUTTON1, T_EZ_FIRE0
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_EZ_FIRE1, T_EZ_FIRE2, T_EZ_FIRE3
                                                   O_FIRE | FA | FP | FB, O_FIRE | FA | FP | FB, O_FIRE | FA | FP | FB, O_FIRE | FA | FP | FB,
                                                   // T_EZ_WALLN
                                                   O_WALLN | FZ | FP | FA | FB,
                                                   // T_EZ_DESTR0, T_EZ_DESTR1, T_EZ_DESTR2, T_EZ_DESTR3
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_EZ_NULL
                                                   O_NULL | FZ | FP | FA | FB,
                                                   // T_EZ_ZARAST0, T_EZ_ZARAST1
                                                   O_ZARAST | FZ | FP | FA | FB, O_ZARAST | FZ | FP | FA | FB,
                                                   // T_EZ_ZARAST2, T_EZ_ZARAST3, T_EZ_BUTTON0, T_EZ_SCORE
                                                   O_UNDEF, O_UNDEF, O_UNDEF, O_UNDEF,
                                                   // T_EZ_TRAPN
                                                   O_TRAPN | FZ | FP | FA | FB};

    public final static int tilesGamePanel[]        = {R.integer.TILE_PERSON_DROP, R.integer.TILE_GOLD, R.integer.TILE_BUTTON1};
    // длины счетчиков
    public static final int[]  formatLengths       = {6, 3, 3, 3};

    private static final int ATTR_SSH_COLOR_PANEL_COUNTERS  = 1000 | ATTR_INT;
    private static final int ATTR_SSH_COLOR_STAT_COUNTERS   = 1002 | ATTR_INT;
    private static final int ATTR_SSH_BM_PANEL              = 1003 | ATTR_DRW;
    private static final int ATTR_SSH_COLOR_NUM_LEVEL       = 1004 | ATTR_INT;


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
                                           ATTR_SSH_BM_PANEL, R.drawable.panel,
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
                                            ATTR_SSH_BM_PANEL, R.drawable.panel,
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

    public static final int[] style_tile_lode = {ATTR_SSH_VERT, 8,
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
                                                   ATTR_SSH_VERT, 8,
                                                   ATTR_CLICKABLE, 0,
                                                   ATTR_FOCUSABLE, 0,
                                                   ATTR_SSH_ALIGNED, 1,
                                                   ATTR_SSH_SCALE, TILE_SCALE_MIN,
                                                   ATTR_SSH_GRAVITY, Gravity.CENTER};

    public static final int[] style_dlg_actions = {ATTR_SSH_SHAPE, TILE_SHAPE_ROUND,
                                                   ATTR_SSH_RADII, R.string.radii_dlg};

    public static final int[] style_panel       = {ATTR_SSH_BITMAP_NAME, ATTR_SSH_BM_PANEL | THEME};
}
