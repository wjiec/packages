//
//  FavoriteButton.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/13.
//

import SwiftUI

struct FavoriteButton: View {
    // The binding property wrapper enables you to read and write between a property
    // that stores data and a view that displays and changes the data. Because you use
    // a binding, changes made inside this view propagate back to the data source.
    @Binding var isFavorite: Bool

    var body: some View {
        Button {
            isFavorite.toggle()
        } label: {
            Label("Toggle Favorite", systemImage: isFavorite ? "star.fill" : "star")
                .labelStyle(.iconOnly)
                .foregroundColor(isFavorite ? .yellow : .gray)
        }
    }
}

#Preview {
    Group {
        FavoriteButton(isFavorite: .constant(true))
        FavoriteButton(isFavorite: .constant(false))
    }
}
