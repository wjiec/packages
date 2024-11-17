//
//  MapView.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI
import MapKit

struct MapView: View {
    var coordinates: CLLocationCoordinate2D
    
    // Create a private computed variable that holds the region information for the map.
    private var region: MKCoordinateRegion {
        MKCoordinateRegion(
            center: coordinates,
            span: MKCoordinateSpan(latitudeDelta: 0.2, longitudeDelta: 0.2)
        )
    }
    
    var body: some View {
        // takes a camera position that you initialize with the region.
        // Map(initialPosition: .region(region))

        // This new initializer expects a Binding to a position, which is a
        // bidirectional connection to the value. Use a .constant() binding
        // here because MapView doesn’t need to detect when someone changes
        // the position by interacting with the map.
        Map(position: .constant(.region(region)))
    }
}

#Preview {
    MapView(coordinates: CLLocationCoordinate2D(latitude: 34.011_286, longitude: -116.166_868))
}
