//
//  CategoryItem.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct CategoryItem: View {
    let landmark: Landmark
    
    var body: some View {
        VStack(alignment: .leading) {
            landmark.image
                .renderingMode(.original)
                .resizable()
                .frame(width: 155, height: 155)
                .cornerRadius(5)

            Text(landmark.name)
                .foregroundStyle(.primary)
        }
    }
}

#Preview {
    CategoryItem(landmark: ModelData().landmarks[0])
}
