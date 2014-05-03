DESCRIPTION = "A basic console image for Gumstix boards."
LICENSE = "MIT"
PR = "r0"

IMAGE_FEATURES += "splash package-management ssh-server-openssh"
IMAGE_LINGUAS = "en-us"

inherit core-image

# Gumstix machines individually RDEPEND on the firware they need but we repeat
# it here as we might want to use the same image on multiple different machines.
FIRMWARE_INSTALL = " \
  linux-firmware-sd8686 \
  linux-firmware-sd8787 \
"

TOOLS_INSTALL = " \
  alsa-utils \
  cpufrequtils \
  grep \
  gzip \
  iputils \
  iw \
  dhcp-client \
  init-ifupdown \
  memtester \
  nano \
  ntp \
  sudo \
  tar \
  tslib \
  u-boot-mkimage \
  vim \
  wget \
  zip \
  media-ctl \
  yavta \
  v4l-utils \
  mtd-utils-ubifs \
  coreutils \
  diffutils \
  findutils \
  less \
  mtdev \ 
  raw2rgbpnm \
"

IMAGE_INSTALL += " \
  packagegroup-cli-tools \
  packagegroup-cli-tools-debug \
  ${FIRMWARE_INSTALL} \
  ${TOOLS_INSTALL} \
"

add_custom_smart_config() {
        smart --data-dir=${IMAGE_ROOTFS}/var/lib/smart channel --add gumstix type=rpm-md name="Gumstix Package Repository" baseurl=https://packages.gumstix.com/master/ -y
}
set_gumstix_user() {
	#To allow shutdown/restart
	echo "%sudo ALL=(ALL) ALL" >> ${IMAGE_ROOTFS}/etc/sudoers
}

ROOTFS_POSTPROCESS_COMMAND =+ "set_gumstix_user ; add_custom_smart_config ;"
