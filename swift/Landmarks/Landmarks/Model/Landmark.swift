//
//  Landmark.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import Foundation
import SwiftUI
import CoreLocation

// Adding Codable conformance makes it easier to move data between the structure and a data file.
// You’ll rely on the Decodable component of the Codable protocol later in this section to read data from file.
struct Landmark: Hashable, Codable, Identifiable {
    // The Landmark data already has the id property required by Identifiable protocol;
    // you only need to add a property to decode it when reading the data.
    let id: Int
    var name: String
    var park: String
    var state: String
    var description: String
    var isFavorite: Bool
    var isFeatured: Bool

    // Add an imageName property to read the name of the image
    // from the data, and a computed image property that loads
    // an image from the asset catalog.
    private var imageName: String
    var image: Image {
        Image(imageName)
    }
    var featureImage: Image? {
        isFeatured ? Image(imageName + "_feature") : nil
    }

    private var coordinates: Coordinates
    var locationCoordinate: CLLocationCoordinate2D {
        CLLocationCoordinate2D(latitude: coordinates.latitude, longitude: coordinates.longitude)
    }

    // Add a coordinates property to the structure using a nested
    // Coordinates type that reflects the storage in the JSON data structure.
    struct Coordinates: Hashable, Codable {
        var longitude: Double
        var latitude: Double
    }
    
    let category: Category
    enum Category: String, Codable, CaseIterable {
        case Lakes = "Lakes"
        case Rivers = "Rivers"
        case Mountains = "Mountains"
    }
}
