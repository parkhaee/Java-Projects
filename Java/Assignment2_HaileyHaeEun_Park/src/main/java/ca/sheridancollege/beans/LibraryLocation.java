package ca.sheridancollege.beans;

public enum LibraryLocation {
OAKVILLE("OAKVILLE"), BRAMPTON("BRAMPTON"), MISSISSAUGA("MISSISSAUGA");

String locations;

private LibraryLocation(String locations) {
    this.locations = locations;
}

public String getLibraryLocation() {
    return this.locations;
}
}
