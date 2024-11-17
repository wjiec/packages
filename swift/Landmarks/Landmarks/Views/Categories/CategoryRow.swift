//
//  CategoryRow.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct CategoryRow: View {
    var category: String
    var landmarks: [Landmark]
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(category)
                .font(.headline)
                .padding(.leading)
                .padding(.top, 5)
            
            ScrollView(.horizontal) {
                HStack(alignment: .top, spacing: 0) {
                    ForEach(landmarks) { landmark in
                        // The category item itself is the label for the button, and its
                        // destination is the landmark detail view for the landmark represented
                        // by the card.
                        NavigationLink {
                            LandmarkDetail(landmark: landmark)
                        } label: {
                            CategoryItem(landmark: landmark)
                                .padding(.leading)
                        }

                        
                    }
                }
            }
            .frame(height: 185)
        }
    }
}

#Preview {
    let modelData = ModelData()
    
    NavigationSplitView {
        CategoryRow(
            category: Landmark.Category.Lakes.rawValue,
            landmarks: Array(modelData.landmarks.prefix(3))
        )
    } detail: {
        Text("Detail")
    }
    .environment(modelData)
}
