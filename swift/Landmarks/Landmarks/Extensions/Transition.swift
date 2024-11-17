//
//  Transition.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import Foundation
import SwiftUI

extension AnyTransition {
    static var moveAndFade: AnyTransition {
        // Use the asymmetric(insertion:removal:) modifier to provide different
        // transitions for when the view appears and disappears.
        .asymmetric(
            insertion: .move(edge: .trailing).combined(with: .opacity),
            removal: .scale.combined(with: .opacity)
        )
    }
}
