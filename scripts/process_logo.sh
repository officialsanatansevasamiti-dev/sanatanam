#!/bin/bash
set -e

# Checks both app/src/main/res/logo.png and app/src/main/res/drawable/logo.png
if [ -f "app/src/main/res/logo.png" ]; then
    echo "Found logo at app/src/main/res/logo.png"
    mkdir -p app/src/main/res/drawable
    cp "app/src/main/res/logo.png" "app/src/main/res/drawable/logo.png"
    SRC="app/src/main/res/drawable/logo.png"
elif [ -f "app/src/main/res/drawable/logo.png" ]; then
    echo "Found logo at app/src/main/res/drawable/logo.png"
    SRC="app/src/main/res/drawable/logo.png"
else
    echo "Error: logo.png not found at app/src/main/res/logo.png or app/src/main/res/drawable/logo.png"
    exit 1
fi

BASE="app/src/main/res"

# Generate square & round launcher icons with aspect ratio preserved and safe padding
for spec in mdpi:48 hdpi:72 xhdpi:96 xxhdpi:144 xxxhdpi:192; do
  dir="$BASE/mipmap-${spec%:*}"
  size="${spec#*:}"
  inner_size=$((size * 78 / 100)) # 78% size ensures safe margin so no parts of the logo are cropped by launcher masks
  mkdir -p "$dir"
  rm -f "$dir/ic_launcher.webp" "$dir/ic_launcher_round.webp"
  
  # Square launcher icon with transparent padding, keeping aspect ratio perfectly
  convert "$SRC" -resize "${inner_size}x${inner_size}" \
    -background none -gravity center -extent "${size}x${size}" \
    "PNG32:$dir/ic_launcher.png"
    
  # Round launcher icon with circular mask, keeping aspect ratio and perfect padding
  radius=$((size / 2))
  convert "$dir/ic_launcher.png" \
    \( -size "${size}x${size}" xc:none -fill white \
       -draw "circle $radius,$radius $radius,0" \) \
    -alpha set -compose DstIn -composite "PNG32:$dir/ic_launcher_round.png"
done

# Adaptive icon foreground referencing drawable/logo inside 66dp safe zone
cat << 'EOF' > app/src/main/res/drawable/ic_launcher_foreground.xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:width="66dp"
        android:height="66dp"
        android:gravity="center"
        android:drawable="@drawable/logo" />
</layer-list>
EOF

echo "App icons successfully generated in all mipmap directories with preserved aspect ratio!"
