# Napi Történelmi Események - Applikáció

Az alkalmazás célja hogy a felhasználók Android alkalmazáson keresztül minden nap lekérhessék az évekkel ezelőtt ugyan arra a napra eső történelmi eseményeket, láthassák milyen érdekességek történtek más-más napokon. Az év minden napjára számos érdekességet lehet listázni. A Wikipedia API-n keresztül hatalmas mennyiségű információval lehet edukálni az érdeklődő felhasználókat. Akit egy-egy esemény jobban érdekel, megjelölheti kedvencként, vagy rábökve akár részletes leírást is kaphat róla.






## Csapattagok

- [@armandkoltai](https://www.github.com/armandkoltai)
- [@hokiattila](https://www.github.com/hokiattila)


## Milyen funkciókkal fog rendelkezni az applikáció?

### 1) Kezdőképernyő

Ezen az oldalon lesznek listázva az események. Minden eseményhez tartozik egy thumbnail kép, cím, és rövid leírás. Ez lehetőséget az a felhasználónak hogy felkeltse az érdeklődését, és rövid beleolvasás után eldönthesse, hogy melyik esemény érdekli a felsoroltak közül.

Egy *DatePicker* segítségével kell kiválasztani a hónap/nap dátumot, amire visszamenőlegesen szeretnénk informálódni, ezután a keresés gombra kattintva listázhatjuk az eseményeket.
A lekért adatok egy *RecyclerView*-ban lesznek felsorolva, eseményenként egy-egy *CardView*-ban.

### 2) Részletes nézet

Amennyiben egy adott esemény érdekel minket, lehetőségünk van arra rákattintani, és további részleteket elolvasni. Mindezek mellett a hozzá kapcsoló wikipédia link is megjelenítésre kerül, tehát a felhasználónak lehetősége van a teljes oldalt is megnyitni böngészőben.
Ha az API-n keresztül lekérhető kép is, akkor azt is megjelenítjük neki a nézet tetején.

### 3) Kedvencek

A felhasználónak lehetésge van a számára tetsző eseményeket elmenteni a kedvencek közé. Természetesen az applikáció 2 indítás között ezeket az adatokat eltárolja, így azok visszanézhetőek és gyűjthetőek. 

## Megvalósítás





#### Napi Események lekérdezése API-n keresztül

```http
  GET - https://en.wikipedia.org/api/rest_v1/feed/onthisday/events/{month}/{day}
```

| Paraméter | Típus     | Leírás                |
| :-------- | :------- | :------------------------- |
| `month` | `string` | **Szükséges -** Szűrt Hónap |
| `day` | `string` | **Szükséges -** Szűrt Nap |

Az API JSON formátumban adja vissza az adatokat, amelyek között számos adat szerepel, mint például

- Az eseményhez tartozó borítókép
- Rövid leírás az eseményről
- Wikipédia oldal linkje



## Használt könyvtárak



A fejlesztés során ezeket az Android-os könyvtárat használjuk:
- Retrofit – az API-hívásokhoz,
- Gson – a JSON válaszok Java objektummá alakításához,
- Glide – a képek gyors és hatékony betöltéséhez,
- Room Database – a kedvencek helyi tárolásához.

## Navigáció

Az alkalmazás oldalai közötti navigációt *Android Navigation Component* segítségével valósítjuk meg. A fő navigáció a *BottomNavigationView*-val történik, amely három fő szekciót tartalmaz:

- Dátumalapú keresés
- Kedvencek-re szűrés (Ha arra a dátumra csak a kedvencekbe rakott eseményeket szeretnénk megnézni)
- Kulcsszavas keresés (pl. ha valakit kifejezetten az arra a napra kapcsolódó 2. világháborús események érdekelnek)

## UI / UX

Az applikációt igyekszünk minél inkább a modern standard-ek szerint, letisztul felülettel, könnyű kezelhetőséggel megvalósítani. 
Megjelenését illetően *portré* módban fog működni az alkalmazás.