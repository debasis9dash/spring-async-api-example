class callback-api::mock {
  package {
    ["apache2", "php5"]:
      ensure => latest
  }

  file {
    "/etc/apache2/sites-available/000-default.conf":
      source => "puppet:///modules/callback-api/default-vhost.conf",
      ensure => present;
  }

  exec {
    "enable mod_rewrite":
      command => "/usr/sbin/a2enmod rewrite",
      require => Package["apache2"],
      user    => root,
      unless  => "/usr/sbin/apache2ctl -M | grep rewrite 2>/dev/null",
      notify  => Service["apache2"]
  }

  service {
    "apache2":
      ensure => "running",
      require => [Exec["enable mod_rewrite"], File["/etc/apache2/sites-available/000-default.conf"]]
  }

  file {
    "/var/www/":
      source => "puppet:///modules/callback-api",
      recurse => true,
      ensure => present;
  }
}