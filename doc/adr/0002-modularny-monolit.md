# 2. Modularny Monolit

Data: 2021-05-10

## Status

Zaakceptowany

## Kontekst

Rozważaliśmy architekturę mikroserwisową oraz modularnego monolitu. Dotychczasowo nie mieliśmy doświadczenia z architekturą mikroserwisową. Nasza aplikacja będzie zorientowana wokół jednej, logicznie spójnej usługi i nie przewidujemy, żeby miało się to zmienić. Nie ma wskazań, by aplikacja działała w środowisku rozproszonym.


## Decyzja

Wybraliśmy architekturę modularnego monolitu.

## Konsekwencje

Zyskaliśmy na prostocie wdrożenia i konfiguracji środowiska wdrożeniowego. Efektem jest także szybszy rozwój aplikacji - komunikacja pomiędzy modułami zachodzi na poziomie klas kodu źródłowego, niepotrzebne jest implementowanie nadmiarowej infrastruktury do osiągnięcia tego samego efektu. Na minus zaliczamy brak podjęcia wyzwania praktycznego zastosowania nowej dla naszego zespołu architektury - mniej rozwojowe podejście.
