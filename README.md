# Парсер для языка арифметических выражений

Для сборки приложения выполнить команду `./gradlew installDist` из папки `AEParser`. После чего можно запускать приложение командой `./AEParser <Absolute file path>` из папки `./AEParser/build/install/AEParser/bin/`. Дерево или сообщение об ошибки выводятся в консоль.


# Парсер для языка L

Для сборки приложения выполнить команду `./gradlew installDist` из папки `LParser`. После чего можно запускать приложение командой `./LParser <Absolute file path>` из папки `./LParser/build/install/LPar  ser/bin/`. Ответ или сообщение об ошибки выводятся в консоль.

# Синтаксис языка L

* `OP = {+, -, *, /, %, ==, !=, >, >=, <, <=, &&, ||}`

* `NUM = как раньше`

* `IDENT = как раньше`

* 
`EXPR = E | E OP EXPR`
`E = true | false | CALL | IDENT | NUM | (EXPR)`

* `ASSIGN = (IDENT = EXPR)`

* `FUNC = def IDENT (IDENT*) {S*}`

* `CALL = IDENT ({EXPR ,}*)`

* `IO = read (IDENT) | write (EXPR)`

* `IF = if (EXPR) {S*} | if (EXPR) {S*} else {S*}`

* `WHILE = while (EXPR) {S*}`

* `S = ASSIGN ; | IO ; | WHILE | IF | CALL ; `

* `P = FUNC* S*`

В программе все объявления идут сначала, потом идет последовательность выражений. Нет унарного минуса. Нет "return" - возвращается последнее значение.

Пример:
* `def fun(x) { x = 1;
while(true){
    dosmth();
}
}`

# Синтаксический сахар
* `x++` вместо `x = x + 1`
* `if (c) {} else if (c2) {} else {}` вместо `if (c) {} else {if (c2) {} else {}}`
* `x,y,z = val;` вместо `x = val; y = val, z = val`