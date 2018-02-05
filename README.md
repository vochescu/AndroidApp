# AndroidApp
Public Transport Manager Android Application
Bus Way Bucharest – aplicația care facilitează deplasarea cu mijloacele de transport în comun. O aplicație interactivă în limba engleză care are rolul de a usura modul in care utilizatorii se deplasează cu autobuzele RATB din municipiul București, precum și de a-i încuraja pe aceștia să apeleze la acest sistem de transport în comun. 

Descriere scenariu utilizare

La deschiderea aplicației, primul ecran conține:
-	 titlul
-	 logo-ul Bucureștiului și numele aplicației
-	un meniu dreapta-sus cu 2 opțiuni: About și Feedback
-	 6 butoane : Bus, FavLines, Map, Tickets, Report, Graphic

About
Activitatea conține detalii despre aplicație, precum numele, anul, creatorul, o scurtă descriere, un buton către ecranul Feedback și un rating bar, pentru a da un calificatv aplicației.
Din meniul din dreapta-sus putem să ne întoarcem în Home, ecranul principal, sau să mergem în Feedback.

Feedback
Activitatea conține o serie de câmpuri pe care utilizatorul le va completa. Acestea se refera la informații personale, obligatoriu trebuie completate, dar și informații opționale precum imaginile (încărcate din galerie sau din rețea, cu un url completat de către utilizator).
După ce câmpurile au fost completate corespunzător, la apăsarea butonului SEND, se va crea automat un fișier .csv cu detaliile completate și deasemenea se încearcă crearea unui email la care se va atașa acest fișier. Pentru a selecta aplicația de email preferată se va deschide und dialog box.
Fiecare câmp este validat înainte ca email-ul să fie compus.
Din meniul din dreapta-sus putem naviga in Home, ecranul principal, sau în activitatea About.

Bus
În ecranul Bus avem afișate, dacă există, liniile de autobuz salvate în baza de date locală. Dacă check-box-ul “External Database” este apăsat, atunci se vor încarca liniile din baza de date gobală. Cu ajutorul butonului “Export data” putem globalize datele locale, introducându-le în baza de date globală.
Lista de autobuze conține și un buton în formă de stea, în dreapta fiecărui rând, pentru a declara o anumită linie ca favorită.
Se pot adăuga și noi autobuze, apăsând pe butonul din dreapta jos a ecranului. Se vor specifica detaliile: număr autobuz, stație de plecare, stație de sosire. La apăsarea butonului “Save” se va face salavarea liniei în baza de date.
Liniile se pot edita la click pe rândul dorit din listă. Ecranul apărut conține și un buton pentru ștergere și unul pentru actualizare linie.
Pentru a adăuga o stație la o anumită linie se va apăsa lung pe linia dorită. Se deschide o noua activitate cu lista de stații. Aici opțiunile sunt de adăigare, pe butonul din dreapta-jos, și de editare, apăsare pe rândul pe care se află stația dorită. Editarea înseamnă oferirea a două posibiltăți: actualizare și ștergere din baza de date a stațiilor.
Opțiunea “înapoi în ecranul principal” este dată și de butonul din stânga-sus.

FavLines
În ecranul FavLines sunt afișate toate liniile selectate ca favorite din fereastra Bus. Dacă nicio linie nu a fost setată, atunci se va afișa un mesaj. 
Tot din această activitate există posibilitatea deselectării din lista de favorite a unui traseu prin apăsarea butonului în formă de stea din dreapta fiecărui rând.
La apasarea pe o anumită linie din listă se va deschide un nou ecran cu celelalte linii din baza de date care nu sunt în lista de favorite. Dacă se selectează o anumită linie din aceast ecran, linia care a fost în lista cu trasee favorite înainte de apăsare va fi înlocuită în baza de date cu cea selectată ulterior din noul ecran. 
Este disponibil și un buton pentru a salva liniile favorite într-un fișier .csv.
Opțiunea “înapoi în ecranul principal” este dată și de butonul din stânga-sus.

Map
În activitatea Map este oferită posibilitatea de a vedea pe harta un anumit traseu ales cu butonul “Search Line”. Acest buton deschide un ecran nou pentru a selecta o anumită linie dintr-o listă preluată din rețea. Harta poate fi de tip “satellit” sau “earth”.
Tickets
Activitatea Tickets oferă posibilitatea de a cumpăra un bilet pentru o călătorie cu un anumit autobuz. A fost integrat serviciul RATB de cumpărare a biletelor prin SMS. Astfel, este compus un mesaj către numărul de telefon special oferit de către RATB pentru a taxa biletele de autobuz. După completarea detaliilor este creat un mesaj cu toate informațiile necesare trimiterii și obținerii biletului de călătorie.

Report
Report crează o vizualizare detaliată a linilor de autobuz și a tuturor stațiilor pe care fiecare dintre aceste trasee le are. Ecranul este structurat mai atractiv.
Utilizatorul are posibilitatea de a salva într-un fișier .csv acest raport cu fiecare linie și toate stațiile sale, prin apăsarea butonului din dreapta-jos.

Graphic
Graphic face o analiză a traseelor salvate în baza de date, le împarte în 3 categorii astfel: autobuze cu 1 stație declarată, autobuze cu 2 stații salvate sau autobuze cu 3 stații sau mai multe salvate. Numărând câte trasee se afla în fiecare categorie, se crează un pie-chart cu aceste detalii și o legendă.
