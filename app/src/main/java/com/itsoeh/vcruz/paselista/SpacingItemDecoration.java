package com.itsoeh.vcruz.paselista;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing; // Espacio en píxeles

    public SpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = spacing;  // Agrega espacio en el lado izquierdo
        outRect.right = spacing; // Agrega espacio en el lado derecho
        outRect.top = spacing;   // Agrega espacio en la parte superior
        outRect.bottom = spacing;// Agrega espacio en la parte inferior
    }
}
