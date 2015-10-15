# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.box_url = "https://vagrantcloud.com/ubuntu/boxes/trusty64"
  config.vm.hostname = "callback-api"

  config.vm.provision "shell", :inline => <<-SHELL
      echo \"Europe/Tallinn\" | sudo tee /etc/timezone && dpkg-reconfigure --frontend noninteractive tzdata
      apt-get update
      apt-get install -y puppet
    SHELL

  config.vm.provision :puppet do |puppet|
    puppet.manifests_path = "vagrant/puppet/manifests"
    puppet.module_path = "vagrant/puppet/modules"
    puppet.manifest_file = "default.pp"
    puppet.options = "--verbose --trace --configtimeout 600"
  end

  # Forward apache2 port for callback api
  config.vm.network :forwarded_port, guest: 80, host: 4080
end