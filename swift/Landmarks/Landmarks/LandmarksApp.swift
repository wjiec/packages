//
//  LandmarksApp.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI

@main
struct LandmarksApp: App {
    // Use the @State attribute to initialize a model object the same way you use it to
    // initialize properties inside a view. Just like SwiftUI initializes state in a view
    // only once during the lifetime of the view, it initializes state in an app only once
    // during the lifetime of the app.
    @State private var modelData = ModelData()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                // Create a model instance and supply it to ContentView using the environment(_:) modifier.
                .environment(modelData)
        }
    }
}
