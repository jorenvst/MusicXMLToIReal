<h1>Programma</h1>
<h2>1. installatie en gebruik</h2>
<p>Bij dit programma komen 2 bestanden:</p>
<ul>
    <li>program.jar</li>
    <li>convert.sh</li>
</ul>
<h3>program.jar</h3>
<p>
Dit bestand bevat het eigenlijke programma.</p>
<p>Dit is een Java jar archief. Om het te kunnen gebruiken, moet Java geïnstalleerd staan om de computer.
Indien Java niet geïnstalleerd is, zal het programma niet werken.<br>
</p>

<p>
Om het bestand uit te kunnen voeren, moet het een uitvoerbaar bestand zijn.
Je maakt het uitvoerbaar door onderstaand commando in een terminal uit te voeren:
</p>

<p><code>chmod u+x pad/naar/program.jar</code></p>

<p>
Nu kan je het programma uitvoeren door een aantal <code>.musicxml</code>-bestanden
aan het programma mee te geven zoals gedemonstreerd in onderstaand voorbeeld:
</p>

<p><code>program.jar "Cissy Strut.musicxml"</code></p>

<p>
Het programma zal in de huidige map van waarin het programma werd uitgevoerd een nieuw bestand aanmaken.
Dit bestand kun je openen met IReal Pro.
</p>

<h3>convert.sh</h3>

<p>Dit is een extra bestand dat het eenvoudiger moet maken om het programma uit te voeren</p>
<p>
Dit script zet eerst een <code>.mscz</code> (musescore-bestand) om in een <code>.musicxml</code>-bestand om dan het Java-programma op dat bestand uit te voeren.
</p>

<p>
Om het bestand uit te kunnen voeren, moet het een uitvoerbaar bestand zijn.
Je maakt het uitvoerbaar door onderstaand commando in een terminal uit te voeren:
</p>

<p><code>chmod u+x pad/naar/convert.sh</code></p>

<p>
Ook moet je enkele aanpassingen doen in het convert.sh bestand zelf. Meer info vind je in convert.sh zelf.
Als een lijn moet aangepast worden, staat er <i><b>TE VERANDEREN</b></i> in de commentaar op de lijn erboven.
</p>

<p>
Aan het script geef je één argument mee: het <code>.mscz</code>-bestand dat omgezet moet worden in een IReal Pro bestand.
Onder de motorkap voert hij program.jar uit met een nieuw gegenereerd bestand.
</p>

