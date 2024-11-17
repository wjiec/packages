//
//  ModelData.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import Foundation

// SwiftUI updates a view only when an observable property
// changes and the view’s body reads the property directly.
@Observable
class ModelData {
    // Create an array of landmarks that you initialize from landmarkData.json.
    var landmarks: [Landmark] = load("landmarkData.json")
    
    // Load the hikes array into your model.
    var hikes: [Hike] = load("hikeData.json")
    
    // Include an instance of the user profile that persists even after the user dismisses the profile view.
    var profile: Profile = .default
    
    var categories: [String: [Landmark]] {
        Dictionary(grouping: landmarks, by: { $0.category.rawValue })
    }
    
    // Contains only the landmarks that have isFeatured set to true.
    var features: [Landmark] {        
        landmarks.filter { $0.isFeatured }
    }
}

// Create a load(_:) method that fetches JSON data with a given name from the app’s main bundle.
//
// The load method relies on the return type’s conformance to the Decodable protocol, which is one
// component of the Codable protocol.
func load<T: Decodable>(_ filename: String) -> T {
    print("load resource: ", filename)
    
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
    else {
        fatalError("Could't find \(filename) in main bundle.")
    }
    
    let data: Data
    do {
        data = try Data(contentsOf: file)
    } catch {
        fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
    }
    
    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
    }
}
