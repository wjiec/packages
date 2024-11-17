//
//  LandmarkList.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI

struct LandmarkList: View {
    // The modelData property gets its value automatically, as long as the environment(_:) modifier
    // has been applied to a parent. The @Environment property wrapper enables you to read the model
    // data of the current view. Adding an environment(_:) modifier passes the data object down through
    // the environment.
    @Environment(ModelData.self) private var modelData: ModelData
    
    // State is a value, or a set of values, that can change over time, and
    // that affects a view’s behavior, content, or layout. You use a property
    // with the @State attribute to add state to a view.
    @State private var showFavoritesOnly: Bool = false
    
    // Compute a filtered version of the landmarks list by checking the
    // showFavoritesOnly property and each landmark.isFavorite value.
    private var filteredLandmarks: [Landmark] {
        modelData.landmarks.filter { landmark in
            !showFavoritesOnly || landmark.isFavorite
        }
    }
    
    var body: some View {
        // The navigation split view takes a second input that’s a placeholder
        // for the view that appears after someone makes a selection.
        NavigationSplitView {
            // Lists work with identifiable data. You can make your data identifiable in
            // one of two ways: by passing along with your data a key path to a property that
            // uniquely identifies each element, or by making your data type conform to
            // the Identifiable protocol.
            List/* (filteredLandmarks, id: \.id) */ {
                // You use the $ prefix to access a binding to a state variable, or one of its properties.
                Toggle(isOn: $showFavoritesOnly) {
                    Text("Favorites only")
                }
                
                // To combine static and dynamic views in a list, or to combine two or
                // more different groups of dynamic views, use the ForEach type instead
                // of passing your collection of data to List.
                ForEach(filteredLandmarks) { landmark in
                    // Wrap the returned row in a NavigationLink, specifying the
                    // LandmarkDetail view as the destination.
                    NavigationLink {
                        LandmarkDetail(landmark: landmark)
                    } label: {
                        LandmarkRow(landmark: landmark)
                    }
                }
            }
            // set the title of the navigation bar when displaying the list.
            .navigationTitle("Landmarks")
            // Improve the filtering animation by adding an animation(_:)
            // modifier that begins when the filteredLandmarks value changes.
            .animation(.default, value: filteredLandmarks)
        } detail: {
            // On iPhone, the split view doesn’t need the placeholder, but on
            // iPad the detail pane can be present before someone makes a
            // selection, as you’ll see later in this tutorial.
            Text("Select a Landmark")
        }
    }
}

#Preview {
    LandmarkList()
        .environment(ModelData())
}
