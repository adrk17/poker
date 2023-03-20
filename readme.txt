Zaimplementowane zasady gry:

Poker - dobierany 5 kartowy w którym po rozpoczęciu rozgrywki gracze otrzymują po 5 kart,
następnie zaczyna się faza wymiany kart gdzie gracze decydują jakie karty w talii chcą 
wymienić, po tej fazie następuje faza weryfikacji rąk graczy gdzie określana jest
ręka gracza a także obliczany jest wynik używany do rozstrzygania remisów pomiędzy tymi
samymi rękoma 
np. 2 pary - 2 pary 
gracz 1:[ diamonds : 2 | spades : 2 | hearts : king | spades : king | clubs : ace ]
gracz 1:[ clubs : 3 | hearts : 3 | clubs : king | diamonds : king | clubs : 4 ]
card order: 2 = 1, 3 = 2, ..., 10 = 9, Jack = 10, Queen = 11, King = 12, Ace = 13
punkty gracz 1 = 120113 - [12]-najbardziej znacząca para, [01]- druga para, [13]-pozostała karta
punkty gracz 2 = 120203 - [12]-najbardziej znacząca para, [02]- druga para, [03]-pozostała karta
gracz 2. wygrywa

Sposób uruchomienia:

uruchomienie funkcji main klasy Server oraz klasy Client w modułach poker-server i poker-client 
lub
uruchomienie np. z lini poleceń poker-server-1.0-SNAPSHOT-jar-with-dependencies.jar oraz poker-client-1.0-SNAPSHOT-jar-with-dependencies.jar znajdujące się odpowiednio w modułach poker-server i folderze target oraz poker-client i folderze target

Protokuł komunikacji

Serwer uruchamia grę i jest odpowiedzialny za przeprowadzenie rozgrywki, logikę gry a także walidację ruchu klienta.
Klient jest odbiornikiem wiadomości wysyłanych przez serwer, w określonych sytuacjach czyli w trakcie wymiany kart i decyzji
o nowej rundzie gry klient ma również możliwość wysłania wiadomości do serwera.

Serwer przypisuje klienta do pewnego channelu któremu przypisane jest id-gracza jako attachment na której podstawie serwer w przyszłości potrafi rozpoznać od którego z graczy wiadomość została wysłana i odpowiedniemu graczowi wysłać informację zwrotną

możliwe komunikaty:

-klient wysyła zapytanie o połączenie z serwerem na co serwer reaguje otwarciem nowego kanału i nawiązaniem połączenia
-klient oczekuje aż wszyscy gracze dołączą do rozgrywki
-serwer wysyła wszystkim graczom karty i informuje o możliwości ich wymiany po czym oczekuje na wiadomości zwrotne od wszystkich graczy
-klient wybiera jakie karty odesłać, wysyła wiadomość do serwera gdzie serwer waliduje ruch poprzez użycie parsera i wysyła informację zwrotną graczowi z jego nowymi kartami bądź komunikatem o źle wykonanym ruchu
-klient odbiera komunikat o nowych kartach i oczekuje aż każdy z graczy wykona ruch
-serwer po otrzymaniu komunikatów od każdego z graczy kończy rozgrywkę i wysyła rezultaty do każdego z klientów, następnie przechodzi w stan oczekiwannia na decyzję o rozpoczęciu nowej gry
-klient odbiera rezultaty po czym poprzez wysłanie odpowiedniego znaku komunikuje serwerowi o chęci wzięcia udziału w następnej rundzie gry
-serwer interpretuje komunikaty w taki sposób że gdy każdy komunikat był twierdzący to przechodzi spowrotem do tworzenia nowej gry i rozdania kart a jeżeli co najmniej jeden komunikat jest negatywny to kończy rozgrywkę i wysyła klientowi komunikat o skończeniu gry
-klient reaguje na komunikat wyjściem z gry lub w przypadku nowej rundy odebraniem nowych kart od serwera


