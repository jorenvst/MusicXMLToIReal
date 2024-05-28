package converter.music.factories;

import converter.music.Measure;
import converter.music.Time;
import org.jdom2.Element;

public class MeasureFactory {

    /**
     * build a measure from musicxml
     * @param measure the element that needs to be converted into a Measure
     * @return a new Measure
     */
    public static Measure buildMeasure(Element measure) {
        if (measure.getChild("attributes") != null && measure.getChild("attributes").getChild("time") != null) {
            return new Measure(
                    measure.getChildren("harmony").stream().map(ChordFactory::buildChord).toList(),
                    new Time(
                            Integer.parseInt(measure.getChild("attributes").getChild("time").getChildText("beats")),
                            Integer.parseInt(measure.getChild("attributes").getChild("time").getChildText("beat-type"))
                    ),
                    measure.getAttributeValue("implicit") != null && measure.getAttributeValue("implicit").equals("yes")
            );
        } else {
            return new Measure(
                    measure.getChildren("harmony").stream().map(ChordFactory::buildChord).toList(),
                    measure.getAttributeValue("implicit") != null && measure.getAttributeValue("implicit").equals("yes")
            );
        }
    }
}
