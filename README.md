# Hotel Management System

Ova aplikacija je razvijena u sklopu projekta na predmetu Baze Podataka na Elektrotehničkom fakultetu u Banjoj Luci.

Hotel Management System je aplikacija koja omogućava upravljanje jednostavnim
hotelskim sistemom koji omogućava upravljanje rezervacijama, gostima, sobama, zaposlenima,
uslugama i događajima.

## Funkcionalnosti

### Upravljanje rezervacijama
- Rezervacija sadrži podatke kao što su:
  - Datum prijave (check-in)
  - Datum odjave (check-out)
  - ID gosta
  - ID sobe
  - ID tipa rezervacije
- Svaka rezervacija ima jednog glavnog gosta, ali može uključivati i dodatne goste.
- Sistem omogućava pregled svih gostiju vezanih za jednu rezervaciju.
- Rezervacije mogu biti:
  - Kreirane
  - Uređivane
  - Brisane
- Rezervacije mogu kreirati samo recepcionisti.
- Sistem omogućava pregled svih rezervacija u hotelu.

### Upravljanje sobama
- Dodavanje, uređivanje i brisanje soba u sistemu.
- Svaka soba je definisana:
  - Spratom
  - Tipom sobe
  - Vrstom kreveta (može imati više tipova kreveta)
- Housekeeping zapis:
  - Vodi evidenciju o održavanju (čišćenju)
  - Housekeeping uslugu mogu obavljati samo zaposleni sa pozicijom *housekeeper*
  - Radnik hotela unosi podatke o obavljenom housekeeping-u, uključujući:
    - Koji radnik je izvršio uslugu
    - Kada je usluga obavljena

### Upravljanje zaposlenima
- Tri osnovne vrste zaposlenih:
  - Menadžer
  - Housekeeper
  - Recepcionist
- Svaki zaposleni može biti označen kao *neaktivan*, čime se onemogućava obavljanje funkcija u hotelu.

### Upravljanje uslugama
- Praćenje usluga koje gosti koriste tokom boravka.
- Svi proizvodi i usluge izdvojeni su u dvije tabele:
  - `item` (predstavlja uslugu)
  - `reservation_has_item` (veza između rezervacije i usluge)
- Sistem omogućava izračunavanje finalnog računa (*invoice*) koji uključuje:
  - Ukupnu cijenu boravka
  - Sve korištene usluge
- Račun se automatski generiše na osnovu:
  - Datuma prijave i odjave
  - Tipa sobe
  - Korištenih usluga

### Organizovanje događaja
- Menadžer može kreirati i organizovati događaje.
- Događaji se mogu održavati na različitim lokacijama unutar hotela.
- Moguće vrste događaja:
  - Sastanci
  - Zabave
  - Konferencije
  - Drugi događaji
- Sistem omogućava:
  - Praćenje prisutnih gostiju
  - Evidenciju svih događaja

### Recenzije gostiju
- Gosti mogu ostavljati recenzije o svom boravku.
- Recenzije se sastoje od:
  - Ocjene
  - Opisa
- Na osnovu svih recenzija generiše se prosječna ocjena hotela.
