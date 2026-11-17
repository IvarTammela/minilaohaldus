# Mini laohaldusrakendus

Java konsoolirakendus väikese poe laoseisu haldamiseks. Grupp: TAK25.

## Käivitamine

Topeltklõps `run.bat` peale või terminalis:

```
run.bat
```

Käsitsi:

```
chcp 65001
javac -encoding UTF-8 -d out src\*.java
java -Dsun.stdout.encoding=UTF-8 -cp out Main
```

Kui värvid kuvatakse imelike märkidena (vana konsool), käivita enne `set NO_COLOR=1`.

## Failid

| Fail | Mis see on |
|---|---|
| `Product.java` | Abstraktne toode: nimi, hind, kogus ja nende muutmine |
| `FoodProduct.java` | Toidukaup, lisaväli „parim enne" |
| `ElectronicProduct.java` | Elektroonika, lisaväli garantii kuudes |
| `Inventory.java` | Ladu: hoiab tooteid, otsib, arvutab koguväärtuse |
| `ConsoleUi.java` | Välimus: värvid, päis, tabel, teated |
| `Main.java` | Menüü ja kasutaja sisendi lugemine |

## OOP põhimõtted koodis

**Abstraktsioon** – `Product` on `abstract` klass ja tal on abstraktsed meetodid `getCategory()` ja `getExtraInfo()`. Üldist „toodet" luua ei saa, ainult kindlat liiki toodet. `Product` kirjeldab, mis on kõigil toodetel ühist, ja jätab detailid alamklassidele.

**Pärilus** – `FoodProduct extends Product` ja `ElectronicProduct extends Product`. Alamklassid saavad nime, hinna, koguse ning meetodid `increaseQuantity()`, `getTotalValue()` jne pärandina kaasa; konstruktoris kutsutakse `super(name, price, quantity)`. Ühist koodi ei pea kordama.

**Kapseldamine** – `Product` väljad on `private`. Kogust saab muuta ainult meetoditega `increaseQuantity()` ja `decreaseQuantity()`, mis kontrollivad, et kogus ei läheks negatiivseks. Ka `Inventory` map on `private` ja `getAllProducts()` annab välja ainult muutmatu vaate.

**Polümorfism** – `ConsoleUi.printRow()` kutsub `product.getExtraInfo()`, teadmata, mis liiki toode see on. Toidukaup vastab „parim enne: …", elektroonika „garantii: … kuud". Uue tooteliigi lisamisel ei pea tabeli koodi muutma.

## Andmestruktuur

Tooted on `LinkedHashMap<String, Product>`-is (`Inventory.java`). Võti on toote nimi väiketähtedega.

- **Otsing täpse nime järgi** (`findByName`) on `products.get(nimi)`: map leiab toote kohe, ei pea kõiki läbi käima.
- **Topelt lisamine on välistatud**: `containsKey` kontrollib, kas sama nimi on juba olemas.
- **Osaline otsing** (`searchByName`) käib `products.values()` läbi ja kogub `ArrayList`-i kõik tooted, mille nimes otsitav tekst sisaldub.
- **Linked** hoiab lisamise järjekorda, nii et nimekiri kuvatakse alati samas järjekorras (tavaline `HashMap` segaks järjekorra).

Andmetüübid: `String` nimele ja kuupäevale, `double` hinnale, `int` kogusele ja garantiile, `boolean` (`isOutOfStock()`) otsa saanud toote jaoks.

## Commit'id

```
git log --oneline
```

1. Lisa abstraktne Product klass ning FoodProduct ja ElectronicProduct alamklassid
2. Lisa Inventory klass: toodete lisamine, otsing ja koguväärtus
3. Lisa menüüga konsoolirakendus, otsing, koguse muutmine ja kujundus
4. Lisa README: käivitamine, OOP selgitused ja andmestruktuuri põhjendus
