//
//  LandmarkDetail.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI

struct LandmarkDetail: View {
    @Environment(ModelData.self) private var modelData: ModelData
    var landmark: Landmark
    
    // Compute the index of the input landmark by comparing it with the model data.
    private var landmarkIndex: Int {
        modelData.landmarks.firstIndex(where: { $0.id == landmark.id })!
    }

    var body: some View {
        // add the model data using a Bindable wrapper.
        @Bindable var modelData = modelData

        // Change the container from a VStack to a ScrollView so the user can
        // scroll through the descriptive content, and delete the Spacer, which
        // you no longer need.
        ScrollView {
            // Add your custom MapView to the top of the stack.
            MapView(coordinates: landmark.locationCoordinate)
                // When you specify only the height parameter, the view
                // automatically sizes to the width of its content.
                .frame(height: 300)
            
            // To layer the image view on top of the map view, give the image
            // an offset of -130 points vertically, and padding of -130 points
            // from the bottom of the view.
            //
            // These adjustments make room for the text by moving the image upwards.
            CircleImage(image: landmark.image)
                .offset(y: -130)
                .padding(.bottom, -130)

            VStack(alignment: .leading) {
                HStack {
                    Text(landmark.name)
                        .font(.title)

                    // Use landmarkIndex with the modelData object to ensure that the
                    // button updates the isFavorite property of the landmark stored in
                    // your model object.
                    FavoriteButton(isFavorite: $modelData.landmarks[landmarkIndex].isFavorite)
                }
                
                HStack {
                    Text(landmark.park)
                    Spacer()
                    Text(landmark.state)
                    
                }
                // When you apply a modifier to a layout view like a stack,
                // SwiftUI applies the modifier to all the elements contained in the group.
                .font(.subheadline)
                .foregroundStyle(.secondary)
                
                // Add a divider and some additional descriptive text for the landmark.
                Divider()
                
                VStack(alignment: .leading) {
                    Text("About \(landmark.name)")
                        .font(.title2)
                    Text(landmark.description)
                }
            }
            .padding()
        
            // Add a spacer at the bottom of the outer VStack to
            // push the content to the top of the screen.
            // Spacer()
        }
        // The navigation changes only have an effect when the view
        // is part of a navigation stack.
        .navigationTitle(landmark.name)
        .navigationBarTitleDisplayMode(.inline)
    }
}

#Preview {
    let modelData = ModelData()
    LandmarkDetail(landmark: modelData.landmarks[0])
        .environment(modelData)
}
