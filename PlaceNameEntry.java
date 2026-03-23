public class PlaceNameEntry{
	
	// class attributes
	private String placeName;
	private String municipality;
	private String province;
	private int population;
 	
        // constructor
	public PlaceNameEntry(String placeName, String municipality, String province, int population){
		this.placeName = placeName;
		this.municipality = municipality;
		this.province = province;
		this.population = population;
	}
	
	// getter methods
	public String getPlaceName() {
		return placeName;
	}
        public String getMunicipality() {
                return municipality;
        }
        public String getProvince() {
                return province;
        }
        public int getPopulation() {
                return population;
        }

	//toString method
	public String toString(){
	return placeName + " " + municipality + " " + province + " " + population;
	}
}
