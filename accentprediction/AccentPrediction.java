package accentprediction;

/**
 * Magic happens.
 * @author Varsi
 *
 */
public class AccentPrediction implements AccentPredictionHandler {

    @Override
    public String getAccentsString(String in) {
	return in;
    }

    @Override
    public String getNoAccentsString(String in) {
	char[] search = {'á', 'í', 'é', 'ó', 'ö', 'ő', 'ú', 'ü', 'ű'};
	char[] replace = {'a', 'i', 'e', 'o', 'o', 'o', 'u', 'u', 'u'};
	for (int i = 0; i < search.length; i++) {
	    in = in.replace(search[i], replace[i]);
	}
	return in;
    }

}
