//
//  CategoryHome.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct CategoryHome: View {
    @Environment(ModelData.self) var modelData: ModelData
    @State private var showProfile: Bool = false
    
    var body: some View {
        NavigationSplitView {
            List {
                FeaturedCarousel(features: modelData.features)
                    .frame(height: 300)
                    // Crop view content to within frame boundaries
                    .clipped()
                    // Set the edge insets to zero on both kinds of landmark
                    // previews so the content can extend to the edges of the display.
                    .listRowInsets(EdgeInsets())

                ForEach(modelData.categories.keys.sorted(), id: \.self) { category in
                    CategoryRow(category: category, landmarks: modelData.categories[category]!)
                }
            }
            // Add the listStyle modifier to pick a list style that better
            // suits the content.
            .listStyle(.inset)
            .navigationTitle("Featured")
            .toolbar {
                // add a user profile button to the navigation bar using the
                // toolbar modifier, and present the ProfileHost view when the
                // user taps it.
                Button {
                    showProfile.toggle()
                } label: {
                    Label("User Profile", systemImage: "person.crop.circle")
                }
            }
            .sheet(isPresented: $showProfile) {
                ProfileHost()
            }
        } detail: {
            Text("Select a Landmark")
        }
    }
}

#Preview {
    CategoryHome()
        .environment(ModelData())
}
